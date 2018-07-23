package com.amondel.checklist

import grails.gorm.transactions.Transactional

@Transactional
class ReleaseManagerService {

    def getReleasePackages(showCompleted,showNotStarted=true) {

        ReleasePackage.withCriteria {
            showCompleted ? isNotNull(completedTime) : ''
            showNotStarted ?  isNull('completedTime'): ''
        }
    }
}
