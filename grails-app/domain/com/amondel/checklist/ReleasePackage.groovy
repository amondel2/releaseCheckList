package com.amondel.checklist

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes=['id'])
@Resource(uri='/ReleasePackage', formats=['json', 'xml'])
class ReleasePackage implements Serializable {

    static constraints = {
        name nullable: false, blank: false, unique: ['releaseName']
        orderNumber nullable: false, blank: false, min: 1
        startTime nullable: true, blank: true
        isPreRelease blank: false, nullable: true
        isPostRelease blank: false, nullable: true
        completedTime nullable: true, blank: false
    }

    static mapping = {
        version false
        id generator:'assigned'
        sort orderNumber: "asc"

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

    Integer getDuration(predicted=false){
        Integer sum = 0
        this.releasePackageItems?.each {
            if (!it.isComplete || predicted) {
                Integer dur = it.getDuration()
                if (dur > sum) {
                    sum = dur
                }
            }
        }
        sum
    }



    @Override
    String toString(){
        this.name
    }

    static belongsTo = [releaseName:ReleaseName]
    static hasMany = [releasePackageItems:ReleasePackageItems]

    Integer orderNumber
    ReleaseName releaseName
    Date completedTime
    Date startTime
    Boolean isPreRelease
    Boolean isPostRelease
    Boolean isComplete
    String id
    String name
}