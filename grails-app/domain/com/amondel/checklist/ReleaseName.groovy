package com.amondel.checklist

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes=['id'])
@Resource(uri='/ReleaseName', formats=['json', 'xml'])
class ReleaseName implements Serializable {

    static constraints = {
        name nullable: false, unique: true,blank: false
        startTime nullable: true
        endTime nullable: true
        plannedStartTime nullable: true

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

    Integer getDuration(predicted=false){
        Integer sum = 0
        this.releasePackages?.each{
            if(!it.isPostRelease && !it.isPreRelease) {
                sum += it.getDuration(predicted)
            }
        }
        sum
    }

    Date getPredicatedEndTime() {
        getPredicatedEndTime(getDuration())
    }

    Date getPredicatedEndTime(Integer duration) {
        def cal = Calendar.getInstance()
        if(startTime) {
            cal.setTime(startTime)
        } else if  (plannedStartTime) {
            cal.setTime(plannedStartTime)
        }
        cal.add(Calendar.MINUTE,duration)
        cal.getTime()
    }

    @Override
    String toString(){
        this.name
    }

    static hasMany = [releasePackages:ReleasePackage]

    String id
    String name
    Date startTime
    Date endTime
    Date plannedStartTime

}