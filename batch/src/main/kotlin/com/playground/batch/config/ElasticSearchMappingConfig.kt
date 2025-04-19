package com.playground.batch.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext

@Configuration
class ElasticSearchMappingConfig {

    @Bean
    fun elasticSearchConverter(mappingContext: SimpleElasticsearchMappingContext) =
         MappingElasticsearchConverter(mappingContext, null)
            .apply { this.afterPropertiesSet() }

}

