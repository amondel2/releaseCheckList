package com.amondel.checklist

import grails.gorm.services.Service

@Service(ReleaseItem)
interface ReleaseItemService {

    ReleaseItem get(Serializable id)

    List<ReleaseItem> list(Map args)

    Long count()

    void delete(Serializable id)

    ReleaseItem save(ReleaseItem releaseItem)

}