package com.playground.batch.job.insertproduct.repository

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "product")
data class ProductIndex(
    @Id
    val id: Int,
    @Field(type = FieldType.Text)
    val title: String,
    @Field(type = FieldType.Keyword)
    val slug: String,
    @Field(type = FieldType.Double)
    val price: Double,
    @Field(type = FieldType.Text)
    val description: String,
    @Field(type = FieldType.Keyword)
    val images: List<String>
)
