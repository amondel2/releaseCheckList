package com.amondel.checklist

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_USER'])
class ReleaseManagerController {

    static responseFormats = ['html','json', 'xml']
    def releaseManagerService
    ReleasePackageService releasePackageService
    SpringSecurityService springSecurityService

    def index() {
        render(view: "index", model: ['releasePackageList': releaseManagerService.getReleasePackages(true)])
    }

    def getReleasePackages() {
        render(template: "relTabContent", model: ['releasePackageList': releaseManagerService.getReleasePackages(params.isactive == '1'),isActive:params.isactive == '1'])
    }

    def manageRelease() {
        //release checklist
        respond releasePackageService.get(params?.id)
    }

    def getNextItem() {
        def rtn = [:]
        def r = releaseManagerService.getCurrentSection(params?.id,params.forCurrent)
        if(r){
            rtn.status = "SUCCESS"
            rtn.obj = r
        } else {
            rtn.status = "FAIL"
        }

        withFormat {
            json { render rtn as JSON }
            xml { render rtn as XML }
            '*' { render rtn as JSON }
        }
    }

    def saveItem() {
        respond releaseManagerService.saveCurrentItem(params?.id,Boolean.valueOf(params.isChecked))
    }

    def saveSection() {
        respond releaseManagerService.saveCurrentSection(params?.id)
    }

    def saveRelease() {
        respond releaseManagerService.saveRelease(params?.id)
    }

    def getReleaseItems(){
        render(template: "checkedItems", model: [obj: releaseManagerService.getCurrentSectionItems(params),currUserId:springSecurityService.getCurrentUserId()])
    }
}
