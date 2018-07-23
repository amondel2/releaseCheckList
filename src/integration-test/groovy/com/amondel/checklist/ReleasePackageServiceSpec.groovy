package com.amondel.checklist

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ReleasePackageServiceSpec extends Specification {

    ReleasePackageService releasePackageService
    SessionFactory sessionFactory

    private String setupData() {
        // TODO: Populate valid domain instances and return a valid ID
            new ReleasePackage(name:"Secon item", startTime: new Date()).save(flush: true, failOnError: true)
        new ReleasePackage(name:"third item", startTime: new Date()).save(flush: true, failOnError: true)
        new ReleasePackage(name:"fourth item", startTime: new Date()).save(flush: true, failOnError: true)
        new ReleasePackage(name:"fifth item", startTime: new Date()).save(flush: true, failOnError: true)
        //new ReleasePackage(...).save(flush: true, failOnError: true)
        //ReleasePackage releasePackage = new ReleasePackage(...).save(flush: true, failOnError: true)
        //new ReleasePackage(...).save(flush: true, failOnError: true)
        //new ReleasePackage(...).save(flush: true, failOnError: true)

        releasePackageService.list(max: 1, offset: 0)?.get(0)?.id
    }

    void "test get"() {
        String id = setupData()

        expect:
        releasePackageService.get(id) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ReleasePackage> releasePackageList = releasePackageService.list(max: 2, offset: 2)

        then:
        releasePackageList.size() == 2
    }

    void "test count"() {
        setupData()

        expect:
        releasePackageService.count() == 5
    }

    void "test delete"() {
        String releasePackageId = setupData()

        expect:
        releasePackageService.count() == 5

        when:
        releasePackageService.delete(releasePackageId)
        sessionFactory.currentSession.flush()

        then:
        releasePackageService.count() == 4
    }

    void "test save"() {
        when:

        ReleasePackage releasePackage = new ReleasePackage(name:"test_4234234",startTime: new Date())
        releasePackageService.save(releasePackage)

        then:
        releasePackage.id != null
    }
}
