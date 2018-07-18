package com.amondel.checklist

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import org.apache.commons.lang.builder.HashCodeBuilder

@EqualsAndHashCode(includes=['id'])
@Resource(uri='/Release', formats=['json', 'xml'])
class Release implements Serializable {

    static constraints = {
        name nullable: false, blank: false, unique: true
        startTime nullable: false, blank: true
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

    static hasMany = [releaseParallelItems:ReleaseParallelItems]

    Date startTime
    String id
    String name
}