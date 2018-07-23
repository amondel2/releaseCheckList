package com.amondel.checklist


import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

@SuppressWarnings('MethodName')
class ReleaseManagerServiceSpec extends HibernateSpec implements ServiceUnitTest<ReleaseManagerService>{

    List<Class> getDomainClasses() { [ReleasePackage] }

//    void "test getReleasePackages"() {
//        when:
//        new ReleasePackage(name:"2134213213",startTime: new Date()).save()
//        new ReleasePackage(name:"2134213213ddwdqwdwqd",startTime: new Date()).save()
//        new ReleasePackage(name:"21342fff213ddwdqwdwqd",startTime: new Date(), completedTime: new Date()).save()
//        def rtn = service.getReleasePackages(false,true)
//
//        then:
//        rtn.size() == 2
//    }
}
