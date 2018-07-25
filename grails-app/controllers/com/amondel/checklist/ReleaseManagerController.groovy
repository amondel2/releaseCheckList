package com.amondel.checklist

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class ReleaseManagerController {

    static responseFormats = ['html','json', 'xml']
    def releaseManagerService
    ReleasePackageService releasePackageService

    def index() {
        respond releaseManagerService.getReleasePackages(false)
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

    def savePackage() {
        respond releaseManagerService.saveCurrentPackage(params?.id,Boolean.valueOf(params.isChecked))
    }

    def saveItem() {
        respond releaseManagerService.saveCurrentItem(params?.id,Boolean.valueOf(params.isChecked))
    }

    def saveSection() {
        respond releaseManagerService.saveCurrentSection(params?.id)
    }

    def getReleaseItems(){
        render(template: "checkedItems", model: [obj: releaseManagerService.getCurrentSectionItems(params)])
    }
}
