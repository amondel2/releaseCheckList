package com.amondel.checklist

import grails.rest.Resource
import groovy.transform.EqualsAndHashCode
import org.apache.commons.lang.builder.HashCodeBuilder

@EqualsAndHashCode(includes=['id'])
@Resource(uri='/ReleaseItem', formats=['json', 'xml'])
class ReleaseItem implements Serializable {

    static constraints = {
        name nullable: false, unique: true,blank: false
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
        this.name
    }

    static hasMany = [releasePackageItems:ReleasePackageItems]


    String id
    String name

}
