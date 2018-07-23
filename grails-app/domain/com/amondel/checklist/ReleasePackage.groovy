package com.amondel.checklist

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import org.apache.commons.lang.builder.HashCodeBuilder
import grails.databinding.BindingFormat

@EqualsAndHashCode(includes=['id'])
@Resource(uri='/ReleasePackage', formats=['json', 'xml'])
class ReleasePackage implements Serializable {

    static constraints = {
        name nullable: false, blank: false, unique: true
        startTime nullable: false, blank: true
        completedTime nullable: true, blank: false
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

    Integer getDuration(){
        Integer sum = 0
        this.releaseParallelItems.each{
            if(!it.isPostRelease && !it.isPreRelease) {
                sum += it.releaseItems.collect { ReleaseItem item ->
                    item.timeNeeded
                }.max()
            }
        }
        sum
    }

    Date getPredicatedEndTime() {
        getPredicatedEndTime(getDuration())
    }

    Date getPredicatedEndTime(Integer duration) {
        def cal = Calendar.getInstance()
        cal.setTime(this.startTime)
        cal.add(Calendar.MINUTE,duration)
        cal.getTime()
    }

    @Override
    String toString(){
        this.name
    }

    static hasMany = [releaseParallelItems:ReleaseParallelItems]

    Date completedTime
    Date startTime
    String id
    String name
}