package com.playground.playground.rag

import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service

@Service
class RAGService(
    private val vectorStore: VectorStore
) {

    fun storeRAGItem(text: String) {
        vectorStore.add(listOf(Document.builder()
            .text(text).build()))
    }

    fun retrieve(query: String): String {
        val searchQuery = SearchRequest.builder()
            .query(query)
            .topK(5)
            .build()

        return vectorStore.similaritySearch(searchQuery)?.firstOrNull()?.text ?: ""
    }
}
