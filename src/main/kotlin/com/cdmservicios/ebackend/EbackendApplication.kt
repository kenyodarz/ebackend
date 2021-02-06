package com.cdmservicios.ebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class EbackendApplication

fun main(args: Array<String>) {
    runApplication<EbackendApplication>(*args)
}
