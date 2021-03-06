package com.amondel.checklist

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class ReleaseItemController {

    static responseFormats = ['html','json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    static scaffold = ReleaseItem
}