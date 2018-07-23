package com.amondel.checklist

import grails.gorm.services.Service

@Service(ReleaseParallelItems)
interface ReleaseParallelItemsService {

    ReleaseParallelItems get(Serializable id)

    List<ReleaseParallelItems> list(Map args)

    Long count()

    void delete(Serializable id)

    ReleaseParallelItems save(ReleaseParallelItems releaseParallelItems)

}