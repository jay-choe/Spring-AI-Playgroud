package com.playground.batch.job.insertproduct

import com.playground.batch.job.insertproduct.dto.Product
import com.playground.batch.job.insertproduct.dto.toIndex
import com.playground.batch.job.insertproduct.repository.ProductIndexRepository
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemReader
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class InsertProductStepConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager
) {

    @Bean("insertProductStep")
    @JobScope
    fun insertProductStep(productIndexRepository: ProductIndexRepository): Step {

        return StepBuilder("insertJobStep", jobRepository)
            .tasklet({ _, _ ->

                val webClient = WebClient.builder()
                    .baseUrl("https://api.escuelajs.co")
                    .build()
                val response = webClient.get()
                    .uri("/api/v1/products")
                    .retrieve()
                    .bodyToFlux(Product::class.java)
                    .collectList()
                    .block() ?: emptyList()

                response.map { it.toIndex() }.forEach{ productIndexRepository.save(it) }


                RepeatStatus.FINISHED
            }, transactionManager)
            .build()
    }

    @Bean
    fun insertProductItemReader(): ItemReader<List<Product>> {
        val webClient = WebClient.builder()
            .baseUrl("https://api.escuelajs.co")
            .build()

        return ItemReader {
            webClient.get()
                .uri("/api/v1/products")
                .retrieve()
                .bodyToFlux(Product::class.java)
                .collectList()
                .block()
        }
    }
}
