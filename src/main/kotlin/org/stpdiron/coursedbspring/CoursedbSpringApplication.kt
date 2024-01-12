package org.stpdiron.coursedbspring

import com.github.kotlintelegrambot.Bot
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication

@SpringBootApplication
class CoursedbSpringApplication(private val bot: Bot) : CommandLineRunner {
    private val logger = KotlinLogging.logger {}

    override fun run(vararg args: String?) {
        logger.info { "Hi!" }
        bot.startPolling()
    }

}

fun main(args: Array<String>) {
    SpringApplicationBuilder(CoursedbSpringApplication::class.java)
        .web(WebApplicationType.NONE)
        .run(*args)
}