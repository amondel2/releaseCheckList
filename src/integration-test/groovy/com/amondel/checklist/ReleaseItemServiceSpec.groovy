package com.amondel.checklist

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ReleaseItemServiceSpec extends Specification {

    ReleaseItemService releaseItemService
    SessionFactory sessionFactory

    private String setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ReleaseItem(...).save(flush: true, failOnError: true)
        //new ReleaseItem(...).save(flush: true, failOnError: true)
        //ReleaseItem releaseItem = new ReleaseItem(...).save(flush: true, failOnError: true)
        //new ReleaseItem(...).save(flush: true, failOnError: true)
        //new ReleaseItem(...).save(flush: true, failOnError: true)
        releaseItemService.list(max: 1, offset: 1)?.get(0)?.id
    }

    void "test get"() {


        String id = setupData()

        expect:
        releaseItemService.get(id) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ReleaseItem> releaseItemList = releaseItemService.list(max: 2, offset: 2)

        then:
        releaseItemList.size() == 2

    }

    void "test count"() {
        setupData()

        expect:
        releaseItemService.count() == 5
    }

    void "test delete"() {
        String releaseItemId = setupData()

        expect:
        releaseItemService.count() == 5

        when:
        releaseItemService.delete(releaseItemId)
        sessionFactory.currentSession.flush()

        then:
        releaseItemService.count() == 4
    }

    void "test save"() {
        when:
        ReleaseItem releaseItem = new ReleaseItem(name:"myTest_324324",releaseSection: ReleaseParallelItems.first())
        releaseItemService.save(releaseItem)

        then:
        releaseItem.id != null
    }
}
