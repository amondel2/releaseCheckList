package com.amondel.checklist

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import org.hibernate.*

@Transactional
class ReleaseManagerService {

    SpringSecurityService springSecurityService
    SessionFactory sessionFactory

    def getReleases(ReleaseStatus type) {
        ReleaseName.withCriteria {
            switch (type) {
                case ReleaseStatus.InProgress :
                    isNull( 'endTime')
                    isNotNull ('startTime')
                    order 'startTime', 'asc'
                    break
                case ReleaseStatus.NotStarted:
                    isNull ('startTime')
                    isNull( 'endTime')
                    order 'plannedStartTime', 'asc'
                    break
                case ReleaseStatus.Completed:
                    isNotNull ('startTime')
                    isNotNull ('endTime')
                    order 'endTime', 'desc'
                    break
                case ReleaseStatus.NotFinished:
                    isNull( 'endTime')
                    order 'plannedStartTime', 'asc'
                    break
            }

        }
    }

    def startRelease(relId) {
        ReleaseName rm = ReleaseName.load(relId)
        if(!rm.startTime) {
            rm.startTime = new Date()
            rm.save()
        }
        rm
    }

    def unComplateSection(sectionId) {
        try {
            ReleasePackage rp = ReleasePackage.findById(sectionId)
            rp.isComplete = false
            rp.completedTime = null
            def query = ReleasePackageItems.where {
                releasePackage == rp
            }
            rp.save(flush: true)
            query.updateAll([endTime: null, isComplete: false, completedUser: null])
            [status: "SUCCESS"]
        } catch (Exception e) {
            []
        }
    }

    def saveCurrentItem(relId,isChecked) {
        ReleasePackageItems ri = ReleasePackageItems.findById(relId)
        if(isChecked) {
            ri.isComplete = true
            ri.endTime = new Date()
            ri.completedUser = springSecurityService.getCurrentUser()
        } else {
            ri.isComplete = false
            ri.endTime = null
            ri.completedUser = null
        }
        ri.save(flush:true,failOnError:true)
        [status:"SUCCESS"]
    }

    def saveCurrentSection(relId) {
        ReleasePackage ri = ReleasePackage.findById(relId)
        ri.isComplete = true
        ri.completedTime = new Date()
        ri.save(flush:true,failOnError:true)
        [status:"SUCCESS"]
    }

    def completeRelease(relId) {
        ReleaseName rp = ReleaseName.load(relId)
        rp.endTime = new Date()
        rp.save(flush:true)
        [status:"SUCCESS"]
    }

    def getCurrentSection(relId,isCurrItem) {
        def hibSession = sessionFactory?.getCurrentSession()
        hibSession?.flush()
        def items
        if(isCurrItem?.toString().equals('prev')) {
            items = ReleasePackage.withCriteria {
                    releaseName{
                        eq('id',relId)
                    }
                order 'orderNumber', 'desc'
                eq('isComplete', true)
            }

        } else {
            items = ReleasePackage.withCriteria {
                releaseName{
                    eq('id',relId)
                }
                order 'orderNumber', 'asc'
                or {
                    eq('isComplete', false)
                    isNull('isComplete')
                }
            }

        }
        if(isCurrItem?.toString().equals('prev')) {
            try {
                return items?.getAt(0)
            } catch (IndexOutOfBoundsException e) {
                return []
            }
        } else if(isCurrItem && Boolean.valueOf(isCurrItem)) {
            try {
                ReleasePackage item = items.getAt(0)
                if(!item?.startTime) {
                    item.startTime = new Date()
                    item.save()
                    def query = ReleasePackageItems.where {
                        releasePackage == item
                    }
                    query.updateAll(startTime: item.startTime)
                } else {
                    def query = ReleasePackageItems.where {
                        releasePackage == item
                        startTime == null
                    }
                    query.updateAll(startTime: item.startTime)
                }

                return item
            } catch (IndexOutOfBoundsException e) {
                return []
            }
        } else if(items.size() > 1)  {
            return items.getAt(1)
        } else {
            return []
        }
    }


    def getCurrentSectionItems(param) {
        ReleasePackageItems.withCriteria {
            releasePackage{
                eq('id', param.id)
            }
        }
    }
}
