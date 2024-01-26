package com.example.practicespringai.azureopenai

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AzureOpenAIApplication

fun main(args: Array<String>) {
    runApplication<AzureOpenAIApplication>(*args)
}
