package com.playground.playground.embedding

import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.stereotype.Service

@Service
class EmbeddingService(
    private val embeddingModel: EmbeddingModel
) {
    fun getEmbedding(text: String): FloatArray {
        val embedding = embeddingModel.embed(text)

        return embedding
    }
}
