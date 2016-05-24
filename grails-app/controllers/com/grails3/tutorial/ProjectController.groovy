package com.grails3.tutorial

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*

@Secured('permitAll')
class ProjectController implements ExceptionHandlerTrait {
    ProjectService projectService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        render view: 'index', model: projectService.index(max, params)
    }

    def show() {
        respond projectService.show(params.id as Long)
    }

    def create() {
        respond projectService.create(params)
    }


    def save(Project project) {
        if (project == null) {
            notFound()
            return
        }

        projectService.save(project)

        if (project.hasErrors()) {
            project.discard()
            respond project.errors, view: 'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'project.label', default: 'Project'), project.id])
                redirect project
            }
            '*' { respond project, [status: CREATED] }
        }
    }

    def edit(Project project) {
        respond project
    }

    def update(Project project) {
        if (project == null) {
            notFound()
            return
        }

        projectService.save(project)

        if (project.hasErrors()) {
            project.discard()
            respond project.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'project.label', default: 'Project'), project.id])
                redirect project
            }
            '*' { respond project, [status: OK] }
        }
    }

    def delete(Project project) {

        if (project == null) {
            throw new RuntimeException("Project not found!")
            return
        }

        projectService.delete(project)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'project.label', default: 'Project'), project.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    def search(){
        withForm {
           // good request
            render projectService.search(params) as JSON
        }.invalidToken {
           // bad request
            render([error: 'invalid CSRF token'] as JSON)
        }

    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'project.label', default: 'Project'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
