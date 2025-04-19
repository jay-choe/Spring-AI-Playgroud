package com.playground.batch

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

@SpringBootApplication
class BatchApplication

fun main(args: Array<String>) {
	val context = runApplication<BatchApplication>(*args)
	exitProcess(SpringApplication.exit(context))
}
