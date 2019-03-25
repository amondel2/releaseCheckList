package com.amondel.checklist

import groovy.transform.CompileStatic

@CompileStatic
@Singleton
class Utils {

    String idGenerator(){
        UUID.randomUUID().toString().replaceAll("-", "");
    }

}