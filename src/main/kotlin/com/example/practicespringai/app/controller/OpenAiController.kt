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
class OpenAiController(
    private val chatClient: ChatClient,
    private val streamingChatClient: StreamingChatClient,
) {
    /**
     * average response time: 2 seconds
     */
    @GetMapping("/open-ai/generate")
    fun generate(@RequestParam(defaultValue = "Tell me a joke") message: String): Map<*, *> {
        return mapOf("generation" to chatClient.generate(message))
    }

    @GetMapping("/open-ai/generateStream")
    fun generateStream(@RequestParam(defaultValue = "Tell me a joke") message: String): Flux<ChatResponse> {
        val prompt = Prompt(message)
        return streamingChatClient.generateStream(prompt)
    }

    @GetMapping("/open-ai/generateStream/content-only")
    fun generateStreamContentOnly(@RequestParam(defaultValue = "Tell me a joke") message: String): Flux<String> {
        val prompt = Prompt(message)
        return streamingChatClient.generateStream(prompt)
            .filter { !it.isFinished() }
            .map { it.generation.content }
    }

    /**
     * content 가 null 또는 finishReason 이 STOP 인 경우 마지막 응답으로 간주
     * TODO 정확한 문서는 찾지 못해 임의로 판단함
     */
    private fun ChatResponse.isFinished(): Boolean {
        return this.generation.content == null
            || this.generation.choiceMetadata.finishReason == "STOP"
    }
}
