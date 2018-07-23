package com.amondel.checklist

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN'])
class ReleaseParallelItemsController {

    ReleaseParallelItemsService releaseParallelItemsService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond releaseParallelItemsService.list(params), model:[releaseParallelItemsCount: releaseParallelItemsService.count()]
    }

    def show(Long id) {
        respond releaseParallelItemsService.get(id)
    }

    def create() {
        respond new ReleaseParallelItems(params)
    }

    def save(ReleaseParallelItems releaseParallelItems) {
        if (releaseParallelItems == null) {
            notFound()
            return
        }

        try {
            releaseParallelItemsService.save(releaseParallelItems)
        } catch (ValidationException e) {
            respond releaseParallelItems.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'releaseParallelItems.label', default: 'ReleaseParallelItems'), releaseParallelItems.id])
                redirect releaseParallelItems
            }
            '*' { respond releaseParallelItems, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond releaseParallelItemsService.get(id)
    }

    def update(ReleaseParallelItems releaseParallelItems) {
        if (releaseParallelItems == null) {
            notFound()
            return
        }

        try {
            releaseParallelItemsService.save(releaseParallelItems)
        } catch (ValidationException e) {
            respond releaseParallelItems.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'releaseParallelItems.label', default: 'ReleaseParallelItems'), releaseParallelItems.id])
                redirect releaseParallelItems
            }
            '*'{ respond releaseParallelItems, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        releaseParallelItemsService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'releaseParallelItems.label', default: 'ReleaseParallelItems'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'releaseParallelItems.label', default: 'ReleaseParallelItems'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
