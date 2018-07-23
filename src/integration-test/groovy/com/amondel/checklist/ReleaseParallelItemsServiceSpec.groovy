package com.amondel.checklist

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ReleaseParallelItemsServiceSpec extends Specification {

    ReleaseParallelItemsService releaseParallelItemsService
    SessionFactory sessionFactory

    private String setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        ReleasePackage rp = ReleasePackage.findOrSaveWhere(name:"First Release")
         new ReleaseParallelItems(isPreRelease:false,isPostRelease: false,releasePackage: rp, orderNum: 4, description: "number 4").save(flush: true, failOnError: true)
        new ReleaseParallelItems(isPreRelease:false,isPostRelease: false,releasePackage: rp, orderNum: 5, description: "number 5").save(flush: true, failOnError: true)
        //new ReleaseParallelItems(...).save(flush: true, failOnError: true)
        //ReleaseParallelItems releaseParallelItems = new ReleaseParallelItems(...).save(flush: true, failOnError: true)
        //new ReleaseParallelItems(...).save(flush: true, failOnError: true)
        //new ReleaseParallelItems(...).save(flush: true, failOnError: true)
        ReleaseParallelItems.first()?.id
        //releaseParallelItems.id
    }

    void "test get"() {
        String id = setupData()

        expect:
        releaseParallelItemsService.get(id) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ReleaseParallelItems> releaseParallelItemsList = releaseParallelItemsService.list(max: 2, offset: 2)

        then:
        releaseParallelItemsList.size() == 2

    }

    void "test count"() {
        setupData()

        expect:
        releaseParallelItemsService.count() == 5
    }

    void "test delete"() {
        String releaseParallelItemsId = setupData()

        expect:
        releaseParallelItemsService.count() == 5

        when:
        releaseParallelItemsService.delete(releaseParallelItemsId)
        sessionFactory.currentSession.flush()

        then:
        releaseParallelItemsService.count() == 4
    }

    void "test save"() {
        when:

        ReleaseParallelItems releaseParallelItems = new ReleaseParallelItems(isPreRelease:false,isPostRelease: false, releasePackage: ReleasePackage.first(), orderNum: 20, description: "number 20")
        releaseParallelItemsService.save(releaseParallelItems)

        then:
        releaseParallelItems.id != null
    }
}
