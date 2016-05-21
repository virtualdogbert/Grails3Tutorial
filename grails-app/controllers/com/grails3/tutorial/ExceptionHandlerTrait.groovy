package com.grails3.tutorial
//@Enhances('Controller') good only for plugins because it needs to be pre-compiled
trait ExceptionHandlerTrait{
    def handleRuntimeException(RuntimeException r){
        notFound()
    }
}