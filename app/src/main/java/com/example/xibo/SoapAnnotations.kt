package com.example.xibo

class SoapAnnotations {
    @Target(AnnotationTarget.FIELD)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class SoapElement(val name: String)
}