package com.example.practicespringai.azureopenai.controller

import org.springframework.ai.chat.ChatClient
import org.springframework.ai.chat.ChatResponse
import org.springframework.ai.chat.StreamingChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import java.time.Duration

@RestController
class AzureOpenAiController(
    @Qualifier("azureOpenAiChatClient") private val chatClient: ChatClient,
    @Qualifier("azureOpenAiChatClient") private val streamingChatClient: StreamingChatClient,
) {
    @GetMapping("/azure-open-ai/generate")
    fun generate(@RequestParam(defaultValue = "Tell me a joke") message: String): Map<*, *> {
        return mapOf("generation" to chatClient.call(message))
    }

    @GetMapping("/azure-open-ai/generateStream")
    fun generateStream(@RequestParam(defaultValue = "Tell me a joke") message: String): Flux<ChatResponse> {
        val prompt = Prompt(message)
        return streamingChatClient.generateStream(prompt)
            .delayElements(Duration.ofMillis(300))
            .doFinally() { println("### generateStream Done") }
    }
}
