package com.playground.playground.rag

import com.playground.playground.pdf.PDFProcessingService
import jakarta.annotation.PostConstruct
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class RAGService(
    val pdfProcessingService: PDFProcessingService,
    val vectorStore: VectorStore
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

    @PostConstruct
    fun init() {
        val path = ClassPathResource("/static/kotlin-guide.pdf").url.path
        val result = pdfProcessingService.process(path, Regex(("(?m)(?=^Item\\s+\\d+:)")), 23)

        result.forEach{ storeRAGItem(it) }

        val response = retrieve("List를 잘 사용하고 싶어")
        println("response: $response")
    }

}
