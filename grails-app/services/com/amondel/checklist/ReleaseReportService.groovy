package com.amondel.checklist

import grails.gorm.transactions.Transactional

@Transactional
class ReleaseReportService {

    def getRelease(String id) {
        ReleaseName.load(id)
    }

    def getPackages (ReleaseName rn) {
        ReleasePackage.withCriteria {
            eq('releaseName',rn)
            order("orderNumber",'asc')
        }
    }

    def getItems (ReleasePackage rp ) {
        ReleasePackageItems.withCriteria {
            eq ('releasePackage',rp )
        }
    }
}

