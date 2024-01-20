package com.example.practicespringai.app.controller

import org.springframework.ai.chat.ChatClient
import org.springframework.ai.chat.ChatResponse
import org.springframework.ai.chat.StreamingChatClient
import org.springframework.ai.prompt.Prompt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class TestController(
    private val chatClient: ChatClient,
    private val streamingChatClient: StreamingChatClient,
) {
    /**
     * average response time: 2 seconds
     */
    @GetMapping("/ai/generate")
    fun generate(@RequestParam(defaultValue = "Tell me a joke") message: String): Map<*, *> {
        return mapOf("generation" to chatClient.generate(message))
    }

    @GetMapping("/ai/generateStream")
    fun generateStream(@RequestParam(defaultValue = "Tell me a joke") message: String): Flux<ChatResponse> {
        val prompt = Prompt(message)
        return streamingChatClient.generateStream(prompt)
    }
}
