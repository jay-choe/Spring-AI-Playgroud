package com.playground.batch.job.insertproduct

import com.playground.batch.job.insertproduct.dto.Product
import com.playground.batch.job.insertproduct.dto.toIndex
import com.playground.batch.job.insertproduct.repository.ProductIndex
import com.playground.batch.job.insertproduct.repository.ProductIndexRepository
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class InsertProductStepConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
) {

    @Bean("insertProductStep")
    @JobScope
    fun insertProductStep(repository: ProductIndexRepository): Step {

        return StepBuilder("insertJobStep", jobRepository)
            .chunk<List<Product>, List<ProductIndex>>(500, transactionManager)
            .reader(insertProductItemReader())
            .processor(insertProductItemProcessor())
            .writer(insertProductItemWriter(repository))
            .build()
    }

    @Bean
    fun insertProductItemReader(): ItemReader<List<Product>> {
        var read = false
        val webClient = WebClient.builder()
            .baseUrl("https://api.escuelajs.co")
            .build()

        return ItemReader {
            if (!read) {
                read = true
                webClient.get()
                    .uri("/api/v1/products")
                    .retrieve()
                    .bodyToFlux(Product::class.java)
                    .collectList()
                    .block() ?: emptyList()
            } else {
                null
            }
        }
    }

    @Bean
    fun insertProductItemProcessor(): ItemProcessor<List<Product>, List<ProductIndex>> {
        return ItemProcessor { products ->
            products.map { it.toIndex() }
        }
    }

    @Bean
    fun insertProductItemWriter(productIndexRepository: ProductIndexRepository): ItemWriter<List<ProductIndex>> {

        return ItemWriter { products ->
            products.items.flatten().forEach { productIndexRepository.updatePrice(it.id, 9.9) }
        }
    }

}
