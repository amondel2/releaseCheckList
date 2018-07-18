package com.amondel.checklist

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import org.apache.commons.lang.builder.HashCodeBuilder


@EqualsAndHashCode(includes=['id'])
@Resource(uri='/ReleaseParallelItems', formats=['json', 'xml'])
class ReleaseParallelItems implements Serializable {

    static constraints = {
        orderNum nullable: false, min:1, unique: ['release'], blank: false
        description blank: false, nullable: false
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


    static belongsTo=[release:Release]
    static hasMany=[releaseItems:ReleaseItem]

    Integer orderNum
    Release release
    String description
}
