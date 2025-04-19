package com.playground.batch.job.insertproduct

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InsertProductJobConfig(
    private val jobRepository: JobRepository
) {

    @Bean
    fun insertProductJob(
        @Qualifier("insertProductStep") insertProductStep: Step
    ): Job =
        JobBuilder("insertProductJob", jobRepository)
            .start(insertProductStep)
            .build()
}
