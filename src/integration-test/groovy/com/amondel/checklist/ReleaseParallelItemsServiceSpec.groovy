package com.amondel.checklist

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class releasePackageItemsServiceSpec extends Specification {

    releasePackageItemsService releasePackageItemsService
    SessionFactory sessionFactory

    private String setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        ReleasePackage rp = ReleasePackage.findOrSaveWhere(name:"First ReleaseName")
         new releasePackageItems(isPreRelease:false,isPostRelease: false,releasePackage: rp, orderNum: 4, description: "number 4").save(flush: true, failOnError: true)
        new releasePackageItems(isPreRelease:false,isPostRelease: false,releasePackage: rp, orderNum: 5, description: "number 5").save(flush: true, failOnError: true)
        //new releasePackageItems(...).save(flush: true, failOnError: true)
        //releasePackageItems releasePackageItems = new releasePackageItems(...).save(flush: true, failOnError: true)
        //new releasePackageItems(...).save(flush: true, failOnError: true)
        //new releasePackageItems(...).save(flush: true, failOnError: true)
        releasePackageItems.first()?.id
        //releasePackageItems.id
    }

    void "test get"() {
        String id = setupData()

        expect:
        releasePackageItemsService.get(id) != null
    }

    void "test list"() {
        setupData()

        when:
        List<releasePackageItems> releasePackageItemsList = releasePackageItemsService.list(max: 2, offset: 2)

        then:
        releasePackageItemsList.size() == 2

    }

    void "test count"() {
        setupData()

        expect:
        releasePackageItemsService.count() == 5
    }

    void "test delete"() {
        String releasePackageItemsId = setupData()

        expect:
        releasePackageItemsService.count() == 5

        when:
        releasePackageItemsService.delete(releasePackageItemsId)
        sessionFactory.currentSession.flush()

        then:
        releasePackageItemsService.count() == 4
    }

    void "test save"() {
        when:

        releasePackageItems releasePackageItems = new releasePackageItems(isPreRelease:false,isPostRelease: false, releasePackage: ReleasePackage.first(), orderNum: 20, description: "number 20")
        releasePackageItemsService.save(releasePackageItems)

        then:
        releasePackageItems.id != null
    }
}
