package com.playground.batch.job.insertproduct.repository

interface CustomProductIndexRepository {
    fun updatePrice(productId: Int, price: Double)
}
