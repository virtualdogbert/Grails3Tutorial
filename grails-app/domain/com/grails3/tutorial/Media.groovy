package com.grails3.tutorial

class Media {
    String  path //relative path to the media
    String  name
    String  mediaType
    Project project
    Report  report
    Boolean isSample = false

    static constraints = {
        project nullable: true
        report nullable: true
    }
}
