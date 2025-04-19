package com.playground.batch.job.insertproduct.dto

import com.playground.batch.job.insertproduct.repository.ProductIndex

fun Product.toIndex(): ProductIndex =
    ProductIndex(
        id = this.id,
        title = this.title,
        slug = this.slug,
        price = this.price,
        images = this.images,
        description = this.description,
    )

