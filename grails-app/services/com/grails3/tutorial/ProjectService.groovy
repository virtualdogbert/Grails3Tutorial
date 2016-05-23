package com.grails3.tutorial

import grails.core.GrailsApplication
import grails.transaction.Transactional
import org.springframework.beans.factory.annotation.Value

@Transactional(readOnly = true)
class ProjectService {
    GrailsApplication grailsApplication

    @Value('${root.image.url}')
    String rootURL

    def index(Integer max, params) {
        params.max = Math.min(max ?: 10, 100)
        [projectList: Project.list(params), projectCount: Project.count()]
    }

    def show(Long projectId) {
        Project.get(projectId)
    }

    def create(params) {
        new Project(
                name: params.name,
                description: params.description,
                image: params.image,
                isSample: params.isSample
        )
    }

    @Transactional
    def save(Project project) {
        project.save()
    }

    @Transactional
    def update(Project project) {
        return project.save()
    }

    @Transactional
    def delete(Project project) {

        if (project == null) {
            transactionStatus.setRollbackOnly()
            throw new RuntimeException("Project not found!")
            return
        }

        project.delete()

    }

    def search(params) {
        Project.where {name =~ "%$params.name%" && isSample == Boolean.parseBoolean(params.isSample)}.list().collect { project ->
        //Project.findAllByNameLikeAndIsSample("%$params.name%", Boolean.parseBoolean(params.isSample)).collect { project ->
            [
                    name       : project.name,
                    description: project.description,
                    image      : project.image,
                    isSample   : project.isSample,
                    reports    : project.reports.collect { report ->
                        [
                                name : report.name,
                                body : report.body,
                                media: report.media.collect { media ->
                                    [
                                            url      : "${grailsApplication.config.root.image.url}/$media.path/$media.name",
                                            path     : media.path,
                                            name     : media.name,
                                            mediaType: media.mediaType,
                                            isSample : media.isSample
                                    ]
                                }
                        ]
                    },
                    media      : project.media.collect { media ->
                        [
                                url      : "$rootURL/$media.path/$media.name",
                                path     : media.path,
                                name     : media.name,
                                mediaType: media.mediaType,
                                isSample : media.isSample
                        ]
                    }

            ]

        }
    }
}
