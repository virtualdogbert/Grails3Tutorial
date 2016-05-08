package com.grails3.tutorial

class Report {
    String  name
    String  body = '{}'
    Project project

    static constraints = {
        name unique: 'project', size: 5..256
        body type: 'text'
    }
}
