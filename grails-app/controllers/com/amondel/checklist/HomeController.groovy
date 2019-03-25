package com.amondel.checklist

import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class HomeController {

    def releaseManagerService

    def index() {

        def currelease = releaseManagerService.getReleases(ReleaseStatus.InProgress)
        def uprelease = releaseManagerService.getReleases(ReleaseStatus.NotStarted)
        def completerelease = releaseManagerService.getReleases(ReleaseStatus.Completed)


        render(view:"index",model:[inprog:currelease,uprelease:uprelease,completerelease:completerelease])
    }
}
