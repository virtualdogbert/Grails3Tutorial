package com.grails3.tutorial

import grails.transaction.Transactional

@Transactional(readOnly = true)
class ProjectService {

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
}
