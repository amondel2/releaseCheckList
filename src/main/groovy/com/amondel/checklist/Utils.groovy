package com.amondel.checklist

import groovy.transform.CompileStatic

@CompileStatic
class Utils {

    private Utils() {

    }

    public static Utils getInstance() {
        if (single_instance == null)
            single_instance = new Utils();
        return single_instance;
    }


    private static Utils single_instance = null;

    String idGenerator(){
        UUID.randomUUID().toString().replaceAll("-", "");
    }

}