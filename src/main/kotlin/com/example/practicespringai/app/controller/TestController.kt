package com.example.practicespringai.app.controller

import org.springframework.ai.chat.ChatClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(private val chatClient: ChatClient) {
    /**
     * average response time: 2 seconds
     */
    @GetMapping("/ai/generate")
    fun generate(@RequestParam(defaultValue = "Tell me a joke") message: String): Map<*, *> {
        return mapOf("generation" to chatClient.generate(message))
    }
}
