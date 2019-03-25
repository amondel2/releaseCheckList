package com.amondel.checklist

import grails.plugin.springsecurity.annotation.Secured

@Secured(['IS_AUTHENTICATED_FULLY'])
class ReleaseReportController {

    def releaseReportService

    def index() {
       ReleaseName rn = releaseReportService.getRelease(params.id)
       def releasePackages =  releaseReportService.getPackages(rn)

        render view: 'index', model: [release:rn,packages:releasePackages]


    }
}
