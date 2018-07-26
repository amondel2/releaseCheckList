package com.amondel.checklist

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import org.apache.commons.lang.builder.HashCodeBuilder


@EqualsAndHashCode(includes=['id'])
@Resource(uri='/ReleaseItem', formats=['json', 'xml'])
class ReleaseItem implements Serializable {

    static constraints = {
        name nullable: false, unique: ['releaseSection'],blank: false
        timeNeeded nullable: true, min: 1, blank: false
        startTime nullable: true, blank:false
        endTime nullable: true, blank:false
        user nullable: true, blank: false
        owenedUser nullable: true, blank: false
        releaseSection nullable: false, blank: false
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

    def beforeUpdate() {
        if(!endTime || endTime.equals(null) ) {
            user = null
        } else if (!user || user.equals(null)) {
            endTime = null
        }
    }

    def afterUpdate() {
        if(!endTime || endTime.equals(null)){
            ReleaseParallelItems.where{id == releaseSectionId}.updateAll(isComplete: false)
        }
    }

    def beforeInsert() {
        if (!id || id.equals(null)) {
            id = Utils.getInstance().idGenerator()
        }
    }

    @Override
    String toString(){
        this.name
    }

    static belongsTo = [releaseSection:ReleaseParallelItems]


    //in minutes
    Integer timeNeeded
    Date startTime
    Date endTime
    User user
    User owenedUser
    String id
    String name
    ReleaseParallelItems releaseSection
}
