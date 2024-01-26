package com.example.practicespringai.openai.controller

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
class OpenAiController(
    @Qualifier("openAiChatClient") private val chatClient: ChatClient,
    @Qualifier("openAiChatClient") private val streamingChatClient: StreamingChatClient,
) {
    @GetMapping("/open-ai/generate")
    fun generate(@RequestParam(defaultValue = "Tell me a joke") message: String): Map<*, *> {
        return mapOf("generation" to chatClient.call(message))
    }

    @GetMapping("/open-ai/generateStream")
    fun generateStream(@RequestParam(defaultValue = "Tell me a joke") message: String): Flux<ChatResponse> {
        val prompt = Prompt(message)
        return streamingChatClient.generateStream(prompt)
            .delayElements(Duration.ofMillis(300))
            .doFinally() { println("### generateStream Done") }
    }

    @GetMapping("/open-ai/generateStream/content-only")
    fun generateStreamContentOnly(@RequestParam(defaultValue = "Tell me a joke") message: String): Flux<String> {
        val prompt = Prompt(message)
        return streamingChatClient.generateStream(prompt)
            .filter { !it.isFinished() }
            .map { it.result.output.content }
            .delayElements(Duration.ofMillis(300))
            .doFinally() { println("### generateStreamContentOnly Done") }
    }

    /**
     * content 가 null 또는 finishReason 이 STOP 인 경우 마지막 응답으로 간주
     * todo 정확한 문서는 찾지 못해 임의로 판단함
     */
    private fun ChatResponse.isFinished(): Boolean {
        return this.result.output.content == null
            || this.result.metadata.finishReason == "STOP"
    }
}
