package com.playground.playground.llm

import org.springframework.ai.chat.model.ChatModel
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class LLMQueryService(
    private val chatModel: ChatModel,
) {

    fun query(query: String): String {
        return chatModel.call(query)
    }

    fun streamResponseQuery(query: String): Flux<String> {
        return chatModel.stream(query)
    }
}
