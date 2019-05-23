package com.amondel.checklist

import grails.plugin.springsecurity.annotation.Secured
import org.docx4j.openpackaging.packages.WordprocessingMLPackage

@Secured(['IS_AUTHENTICATED_FULLY'])
class ReleaseReportController {

    def releaseReportService

    def requestService

    def index() {
        ReleaseName rn = releaseReportService.getRelease(params.id)
        def releasePackages = releaseReportService.getPackages(rn)

        render view: 'index', model: [release: rn, packages: releasePackages]


    }

    def getWord() {
        ReleaseName rn = releaseReportService.getRelease(params.id)
        def releasePackages = releaseReportService.getPackages(rn)
        String redirectUrl = requestService.getCurrentUrl(request)
        def contents =  g.render  contextPath:"/common", template: 'relReportTable', model: ['packages':releasePackages]
        def css =  "<link rel=\"stylesheet\" href=\"$redirectUrl/assets/mainapp.css?compile=false\" />"

        String cons = "<html><head>$css</head><body>" +  contents.toString() + "</body></html>"
        WordprocessingMLPackage document = releaseReportService.generateReleaseReport(rn, releasePackages,cons.toString())
        response.setContentType("APPLICATION/OCTET-STREAM")
        response.setHeader("Content-Disposition", "Attachment;Filename=${ReleaseName.toString()}_status.docx")
        def outputStream = response.getOutputStream()
        document.save(outputStream)
        outputStream.flush()
        outputStream.close()

    }
}