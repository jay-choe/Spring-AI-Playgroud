package com.playground.playground.rag

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface RAGItemRepository: ElasticsearchRepository<RagItem, String>
