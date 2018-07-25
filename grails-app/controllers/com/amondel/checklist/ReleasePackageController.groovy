package com.amondel.checklist

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
@Secured(['ROLE_ADMIN'])
class ReleasePackageController {

    ReleasePackageService releasePackageService
    static responseFormats = ['html','json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond releasePackageService.list(params), model:[releasePackageCount: releasePackageService.count()]
    }

    def show(String id) {
        respond releasePackageService.get(id)
    }

    def create() {
        respond new ReleasePackage(params)
    }

    def save(ReleasePackage releasePackage) {
        if (releasePackage == null) {
            notFound()
            return
        }

        try {
            releasePackageService.save(releasePackage)
        } catch (ValidationException e) {
            respond releasePackage.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'releasePackage.label', default: 'ReleasePackage'), releasePackage.id])
                redirect releasePackage
            }
            '*' { respond releasePackage, [status: CREATED] }
        }
    }

    def edit(String id) {
        respond releasePackageService.get(id)
    }

    def update(ReleasePackage releasePackage) {
        if (releasePackage == null) {
            notFound()
            return
        }

        try {
            releasePackageService.save(releasePackage)
        } catch (ValidationException e) {
            respond releasePackage.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'releasePackage.label', default: 'ReleasePackage'), releasePackage.id])
                redirect releasePackage
            }
            '*'{ respond releasePackage, [status: OK] }
        }
    }

    def delete(String id) {
        if (id == null) {
            notFound()
            return
        }

        releasePackageService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'releasePackage.label', default: 'ReleasePackage'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'releasePackage.label', default: 'ReleasePackage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
