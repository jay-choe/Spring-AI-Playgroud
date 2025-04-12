package com.playground.playground.review

import com.playground.playground.llm.LLMQueryService
import com.playground.playground.rag.RAGService
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class AICodeReviewService(
    private val llmQueryService: LLMQueryService,
    private val ragService: RAGService,
) {
    private val preProcessingTemplate = """
    You are a code-cleaning assistant. Please process the following text so that only valid and relevant code remains, while removing any extraneous content. Specifically:

    1. Remove all HTML tags (e.g., <div>, <span>, etc.) and unrelated markup.
    2. Remove or filter out comment lines that are clearly non-essential (e.g., //TODO, # debug info, /*...*/ style comments). 
    3. Keep comments that explain code functionality, parameters, or any essential logic.
    4. Discard any other text or metadata that does not directly contribute to the code.

    At the end, return the cleaned code inside a Markdown fenced code block, like this:

    ```kotlin
        {code}
    ```

    Do not add additional commentary or explanation—only return the cleaned code block.
    """.trimIndent()

    private val reviewTemplate = """
        You are an expert Kotlin code reviewer.
            Below is the Kotlin code snippet and additional context. 
            Please provide a detailed review focusing on Task.

            Code:
                ```
                    {code}
                ```

            Additional Context (from knowledge base):
            {context}

            [Task]
            1. Point out any issues or suboptimal parts in the code (style, performance, maintainability, etc.).
            2. Suggest improvements and explain rationale with references if possible.
            3. Keep your answer concise and clear.
            4. Do not hallucinate
            
    """.trimIndent()

    fun review(code: String): Flux<String> {
        // preprocess with LLM
        val preprocessed = preProcessingTemplate.replace("{code}", code)
        val processedCode = llmQueryService.query(preprocessed)
        val context = ragService.retrieve(code)
        val reviewRequest = reviewTemplate.replace("{code}", processedCode)
                                          .replace("{context}", context)

        val review = llmQueryService.streamResponseQuery(reviewRequest)
        return review
    }
}
