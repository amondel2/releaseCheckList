package com.amondel.checklist

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class ReleaseItemController {

    ReleaseItemService releaseItemService
    static responseFormats = ['html','json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond releaseItemService.list(params), model:[releaseItemCount: releaseItemService.count()]
    }

    def show(String id) {
        respond releaseItemService.get(id)
    }

    def create() {
        respond new ReleaseItem(params)
    }

    def save(ReleaseItem releaseItem) {
        if (releaseItem == null) {
            notFound()
            return
        }

        try {
            releaseItemService.save(releaseItem)
        } catch (ValidationException e) {
            respond releaseItem.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'releaseItem.label', default: 'ReleaseItem'), releaseItem.id])
                redirect releaseItem
            }
            '*' { respond releaseItem, [status: CREATED] }
        }
    }

    def edit(String id) {
        respond releaseItemService.get(id)
    }

    def update(ReleaseItem releaseItem) {
        if (releaseItem == null) {
            notFound()
            return
        }

        try {
            releaseItemService.save(releaseItem)
        } catch (ValidationException e) {
            respond releaseItem.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'releaseItem.label', default: 'ReleaseItem'), releaseItem.id])
                redirect releaseItem
            }
            '*'{ respond releaseItem, [status: OK] }
        }
    }

    def delete(String id) {
        if (id == null) {
            notFound()
            return
        }

        releaseItemService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'releaseItem.label', default: 'ReleaseItem'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'releaseItem.label', default: 'ReleaseItem'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
