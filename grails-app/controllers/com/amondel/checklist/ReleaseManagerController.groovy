package com.amondel.checklist

import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class ReleaseManagerController {

    static responseFormats = ['html','json', 'xml']
    def releaseManagerService

    def index() {

        respond releaseManagerService.getReleasePackages(false)
    }
}
