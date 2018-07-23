package com.amondel.checklist

import grails.gorm.services.Service

@Service(ReleasePackage)
interface ReleasePackageService {

    ReleasePackage get(Serializable id)

    List<ReleasePackage> list(Map args)

    Long count()

    void delete(Serializable id)

    ReleasePackage save(ReleasePackage releasePackage)

}