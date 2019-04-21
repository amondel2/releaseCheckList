package com.amondel.checklist

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class RoleController {

    static responseFormats = ['html','json', 'xml']
    static scaffold = Role
}
