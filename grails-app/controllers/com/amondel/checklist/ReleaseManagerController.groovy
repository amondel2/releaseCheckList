package com.amondel.checklist

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class ReleaseManagerController {

    static responseFormats = ['html','json', 'xml']
    def releaseManagerService
    SpringSecurityService springSecurityService

    def index() {
        respond releaseManagerService.getReleases(ReleaseStatus.NotFinished)
    }

    def manageRelease() {
        respond releaseManagerService.startRelease(params?.id)
    }

    def getNextItem() {
        def rtn = [:]
        def r = releaseManagerService.getCurrentSection(params?.id,params.forCurrent)
        if(r){
            rtn.status = "SUCCESS"
            rtn.obj = r
            rtn.loginUser = springSecurityService.getCurrentUserId()
        } else {
            rtn.status = "FAIL"
        }

        withFormat {
            json { render rtn as JSON }
            xml { render rtn as XML }
            '*' { render rtn as JSON }
        }
    }

    def unComplateSection() {
        respond releaseManagerService.unComplateSection(params.id)
    }

    def saveItem() {
        respond releaseManagerService.saveCurrentItem(params?.id,Boolean.valueOf(params.isChecked))
    }

    def saveSection() {
        respond releaseManagerService.saveCurrentSection(params?.id)
    }

    def getReleaseItems(){
        render(template: "checkedItems", model: [obj: releaseManagerService.getCurrentSectionItems(params), loginUser:springSecurityService.getCurrentUserId()])
    }

    def completeRelease(){
        def rtn = [:]
        try {
            rtn = releaseManagerService.completeRelease(params?.id)
        } catch(Exception e) {
            rtn.status = "FAIL"
        }
        withFormat {
            json { render rtn as JSON }
            xml { render rtn as XML }
            '*' { render rtn as JSON }
        }
    }
}
