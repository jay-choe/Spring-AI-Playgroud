package com.playground.batch.config

import org.springframework.boot.autoconfigure.batch.BatchDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.support.JdbcTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class BatchConfig(
    @BatchDataSource
    private val datasource: DataSource,
) {

    @Bean
    fun transactionManager(): PlatformTransactionManager = JdbcTransactionManager(datasource)
}
