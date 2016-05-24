package com.grails3.tutorial

import com.virtualdogbert.ast.EnforcerException

import static org.springframework.http.HttpStatus.NOT_FOUND

//@Enhances('Controller') good only for plugins because it needs to be pre-compiled
trait ExceptionHandlerTrait {
    def handleRuntimeException(RuntimeException r) {
        notFound(r)
    }

    def handleRuntimeException(EnforcerException e) {
        notFound(e)
    }

    void notFound(Exception e) {
        request.withFormat {
            form multipartForm {
                flash.message = e.message
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}