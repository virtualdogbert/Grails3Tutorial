package com.grails3.tutorial

class Project {
    String  name
    String  description
    String  image = 'default.png' //relative path to the project image
    Boolean isSample = true

    static constraints = {
        name unique: true
    }
}
