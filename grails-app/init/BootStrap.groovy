import com.grails3.tutorial.Media
import com.grails3.tutorial.Project
import com.grails3.tutorial.Report

class BootStrap {

    def init = { servletContext ->
        Project project = new Project(name: 'Project Test-01', description: 'this is a description of project 01', isSample: false).save()
        Report report = new Report(name:'Report-01', body: '{param1:123, param2:234}', project:project).save()
        Media media = new Media(name: 'Report-Image-01.jpg', path: "reports/$report.id", mediaType: 'jpg', report: report).save()

        media = new Media(name: 'Project-Image-01.jpg', path: "projects/$project.id", mediaType: 'jpg', project: project).save()

        report = new Report(name:'Report-02', body: '{param1:123, param2:234}', project:project).save()
        report = new Report(name:'Report-03', body: '{param1:123, param2:234}', project:project).save()

        project = new Project(name: 'Project Test-02', description: 'this is a description of project 02',).save()
        project = new Project(name: 'Project Test-03', description: 'this is a description of project 03', isSample: false).save()
    }
    def destroy = {
    }
}
