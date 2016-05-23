package com.grails3.tutorial

class Project {
    String  name
    String  description
    String  image = 'default.png' //relative path to the project image
    Boolean isSample = true

    //I consider using hasMany as an anti pattern because the has many uses a set by default which grantees both order
    // and uniqueness, and the only way it can do that is to bring the whole collection into memory. While hasMany is
    // good for small collections that do not grow, it can become a performance issue for collections that grow over time
    // See Programming Groovy, Page 147.
    //static hasMany = [reports: Report, media: Media]

    //While this doesn't give you all the convenience as hasMany, it will work better for big collections and you can do
    // projectInstance.reports, because of the way the get methods work.
    def getReports(){
        Report.findAllByProject(this)
    }

    def getMedia(){
        Media.findAllByProject(this)
    }

    static constraints = {
        name unique: true
    }
}
