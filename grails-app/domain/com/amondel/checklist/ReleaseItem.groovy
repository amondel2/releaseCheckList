package com.amondel.checklist

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import org.apache.commons.lang.builder.HashCodeBuilder


@EqualsAndHashCode(includes=['id'])
@Resource(uri='/ReleaseItem', formats=['json', 'xml'])
class ReleaseItem implements Serializable {

    static constraints = {
        name nullable: false, unique: ['release'],blank: false
        timeNeeded nullable: true, min: 1, blank: false
        actualTime nullable: true, blank: false, min: 1
        isCompleted nullable: true, blank: false
        user nullable: true, blank: false
        release nullable: false, blank: false


    }

    static mapping = {
        version false
        id generator:'assigned'
    }

    def beforeValidate() {
        if(!id || id.equals(null)) {
            id  = Utils.getInstance().idGenerator()
        }
    }

    def beforeInsert() {
        if (!id || id.equals(null)) {
            id = Utils.getInstance().idGenerator()
        }
    }

    static belongsTo = [releaseSection:ReleaseParallelItems,user:User]

    //in minutes
    Integer timeNeeded
    Integer actualTime
    Boolean isCompleted
    User user
    String id
    String name
    ReleaseParallelItems releaseSection
}
