package com.playground.batch.job.insertproduct.repository

import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.document.Document
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.UpdateQuery
import org.springframework.stereotype.Repository

@Repository
class CustomProductIndexRepositoryImpl(
    private val searchOperations: ElasticsearchOperations
): CustomProductIndexRepository {
    val coordinates = IndexCoordinates.of("product")

    override fun updatePrice(productId: Int, price: Double) {
        val updateQuery = UpdateQuery.builder(productId.toString())
            .withDocument(Document.from(mapOf("price" to price)))
            .build()

        searchOperations.update(updateQuery, coordinates)
    }
}
