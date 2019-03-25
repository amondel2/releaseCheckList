package com.amondel.checklist

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class ReleaseNameController {

    static responseFormats = ['html','json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    static scaffold = ReleaseName
}