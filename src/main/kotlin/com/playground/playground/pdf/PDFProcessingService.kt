package com.playground.playground.pdf

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Paths

@Service
class PDFProcessingService {

    fun process(path: String, splitRegex: Regex, skipPage: Int = 0): List<String> {
        val filePath = Paths.get(path)
        if (!Files.exists(filePath)) throw IllegalArgumentException("File $path does not exist")

        Files.newInputStream(filePath).use { inputStream ->
            PDDocument.load(inputStream).use { document ->
                val textStripper = PDFTextStripper()
                // page skip
                textStripper.startPage = skipPage + 1
                val text = textStripper.getText(document)

                return text.split(splitRegex)
                    .map { it.trim() }
                    .filter { it.isNotBlank() }
            }
        }
    }
}
