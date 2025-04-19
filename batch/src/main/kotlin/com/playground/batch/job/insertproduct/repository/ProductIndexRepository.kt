package com.playground.batch.job.insertproduct.repository

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductIndexRepository: ElasticsearchRepository<ProductIndex, Int>, CustomProductIndexRepository
