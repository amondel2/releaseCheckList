package com.amondel.checklist

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes=['id'])
@Resource(uri='/ReleasePackageItems', formats=['json', 'xml'])
class ReleasePackageItems implements Serializable {

    static constraints = {
        orderNum nullable: false, min:1, unique: ['releasePackage'], blank: false
        releasePackage blank: false, nullable: false
        releaseItem nullable: false, blank: false
        timeNeeded nullable: false, blank: false, min:1
        isComplete blank: false, nullable: true
        startTime nullable: true
        endTime nullable: true
        user nullable: true
        completedUser nullable: true
    }

    static mapping = {
        version false
        id generator:'assigned'
        sort orderNum: "asc"
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
    String toString() {
        return this.releasePackage.toString() + " " +  this.releaseItem.toString()

    }

    Integer getDuration(){
        timeNeeded ?: 0
    }




    static belongsTo = [releaseItem:ReleaseItem,releasePackage:ReleasePackage]


    String id
    ReleasePackage releasePackage
    ReleaseItem releaseItem
    User user
    User completedUser
    Integer orderNum
    Boolean isComplete
    //in minutes
    Integer timeNeeded
    Date startTime
    Date endTime
}
