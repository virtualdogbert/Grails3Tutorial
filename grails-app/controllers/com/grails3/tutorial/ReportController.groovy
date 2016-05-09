package com.grails3.tutorial

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ReportController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Report.list(params), model:[reportCount: Report.count()]
    }

    def show(Report report) {
        respond report
    }

    def create() {
        respond new Report(params)
    }

    @Transactional
    def save(Report report) {
        if (report == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (report.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond report.errors, view:'create'
            return
        }

        report.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'report.label', default: 'Report'), report.id])
                redirect report
            }
            '*' { respond report, [status: CREATED] }
        }
    }

    def edit(Report report) {
        respond report
    }

    @Transactional
    def update(Report report) {
        if (report == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (report.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond report.errors, view:'edit'
            return
        }

        report.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'report.label', default: 'Report'), report.id])
                redirect report
            }
            '*'{ respond report, [status: OK] }
        }
    }

    @Transactional
    def delete(Report report) {

        if (report == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        report.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'report.label', default: 'Report'), report.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'report.label', default: 'Report'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
