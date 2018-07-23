package com.amondel.checklist

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes=['id'])
@Resource(uri='/ReleaseParallelItems', formats=['json', 'xml'])
class ReleaseParallelItems implements Serializable {

    static constraints = {
        orderNum nullable: false, min:1, unique: ['releasePackage'], blank: false
        description blank: false, nullable: false
        releasePackage blank: false, nullable: false
        isPreRelease blank: false, nullable: true
        isPostRelease blank: false, nullable: true

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

    @Override
    String toString(){
        this.description
    }


    static belongsTo=[releasePackage:ReleasePackage]
    static hasMany=[releaseItems:ReleaseItem]

    String id
    Integer orderNum
    ReleasePackage releasePackage
    String description
    Boolean isPreRelease
    Boolean isPostRelease
}