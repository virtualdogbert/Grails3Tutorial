import com.grails3.tutorial.Media
import com.grails3.tutorial.Project
import com.grails3.tutorial.Report
import com.grails3Tutorial.security.DomainRole
import com.grails3Tutorial.security.Role
import com.grails3Tutorial.security.User
import com.grails3Tutorial.security.UserRole


class BootStrap {

    def init = { servletContext ->
        Project project = new Project(name: 'Project Test-01', description: 'this is a description of project 01', isSample: false).save(flush: true, failOnError: true)
        Report report = new Report(name:'Report-01', body: '{param1:123, param2:234}', project:project).save(flush: true, failOnError: true)
        Media media = new Media(name: 'Report-Image-01.jpg', path: "reports/$report.id", mediaType: 'jpg', report: report).save(flush: true, failOnError: true)

        media = new Media(name: 'Project-Image-01.jpg', path: "projects/$project.id", mediaType: 'jpg', project: project).save(flush: true, failOnError: true)

        report = new Report(name:'Report-02', body: '{param1:123, param2:234}', project:project).save(flush: true, failOnError: true)
        report = new Report(name:'Report-03', body: '{param1:123, param2:234}', project:project).save(flush: true, failOnError: true)

        project = new Project(name: 'Project Test-02', description: 'this is a description of project 02',).save(flush: true, failOnError: true)
        project = new Project(name: 'Project Test-03', description: 'this is a description of project 03', isSample: false).save(flush: true, failOnError: true)

        Role user_role = new Role(authority: 'ROLE_USER').save(flush: true, failOnError: true)
        Role admin_role = new Role(authority: 'ROLE_ADMIN').save(flush: true, failOnError: true)
        User user = new User(username: 'user', password: 'password').save(flush: true, failOnError: true)
        User admin_user = new User(username: 'admin', password: 'password').save(flush: true, failOnError: true)
        UserRole.create(user, user_role, true)
        UserRole.create(admin_user, user_role, true)
        UserRole.create(admin_user, admin_role, true)

        new DomainRole(domainId: 1, domainName: 'com.grails3.tutorial.Project', role: 'owner', user: user).save(flush: true, failOnError: true)
        user = new User(username: 'user2', password: 'password').save(flush: true, failOnError: true)
        UserRole.create(user, user_role, true)
        new DomainRole(domainId: 1, domainName: 'com.grails3.tutorial.Project', role: 'editor', user: user).save(flush: true, failOnError: true)
        new User(username: 'user3', password: 'password').save(flush: true, failOnError: true)


    }
    def destroy = {
    }
}
