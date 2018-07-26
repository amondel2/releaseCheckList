package com.amondel.checklist

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import org.hibernate.*

@Transactional
class ReleaseManagerService {

    ReleasePackageService releasePackageService
    SpringSecurityService springSecurityService
    SessionFactory sessionFactory

    def getReleasePackages(showActive) {

        ReleasePackage.withCriteria {
            if(showActive) {
                isNull('completedTime')
            } else {
                isNotNull('completedTime')
            }
        }
    }

    def saveCurrentItem(relId,isChecked) {
        ReleaseItem ri = ReleaseItem.findById(relId)
        if(isChecked) {
            ri.endTime = new Date();
            ri.user = springSecurityService.getCurrentUser()
        } else {
            ri.endTime = null
            ri.user = null
        }
        ri.save(flush:true,failOnError:true)
        [status:"Success"]
    }

    def saveCurrentSection(relId) {
        ReleaseParallelItems ri = ReleaseParallelItems.findById(relId)
        ri.isComplete = true
        ri.save(flush:true,failOnError:true)
        [status:"Success"]
    }

    def saveRelease(relId){
        ReleasePackage rp = releasePackageService.get(relId)
        rp.completedTime = new Date();
        rp.save()
        [status:"Success"]

    }

    def getCurrentSection(relId,isCurrItem) {
        def hibSession = sessionFactory?.getCurrentSession()
        hibSession?.flush()
        def items
        if(isCurrItem?.toString().equals('prev')) {
            items = ReleaseParallelItems.withCriteria {
                eq('releasePackage', releasePackageService.get(relId))
                order 'orderNum', 'desc'
                eq('isComplete', true)
            }

        } else {
            items = ReleaseParallelItems.withCriteria {
                eq('releasePackage', releasePackageService.get(relId))
                order 'orderNum', 'asc'
                or {
                    eq('isComplete', false)
                    isNull('isComplete')
                }
            }
        }
        if(isCurrItem?.toString().equals('prev')) {
            try {
                return items?.get(0)
            } catch (IndexOutOfBoundsException e) {
                return []
            }
        } else if(isCurrItem && Boolean.valueOf(isCurrItem) && items.size() > 0) {
            ReleaseItem.where{ releaseSection == items?.get(0) && startTime == null }.updateAll(startTime:new Date())
            return items?.get(0)
        } else if(items.size() > 1)  {
            return items?.getAt(1)
        } else {
            return []
        }
    }


    def getCurrentSectionItems(param) {
        ReleaseItem.withCriteria {
            releaseSection{
                eq('id', param.id)
            }

           order 'timeNeeded', 'desc'
        }
    }
}
