package com.playground.playground.rag

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "rag_documents")
data class RagItem(
    @Id
    val id: String? = null,
    val text: String,
    val embedding: FloatArray
)
