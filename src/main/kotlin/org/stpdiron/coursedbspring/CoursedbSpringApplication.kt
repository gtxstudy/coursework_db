package org.stpdiron.coursedbspring

import com.github.kotlintelegrambot.Bot
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.stpdiron.coursedbspring.repos.UserRepository
import org.stpdiron.coursedbspring.services.DatabaseInitService

@SpringBootApplication
@EnableJdbcRepositories("org.stpdiron.coursedbspring")
class CoursedbSpringApplication(
    private val bot: Bot,
    private val repo: UserRepository,
    private val initService: DatabaseInitService
) : CommandLineRunner {
    private val logger = KotlinLogging.logger {}

    override fun run(vararg args: String?) {
        logger.info { "Hi!" }
        initService.init()
        bot.startPolling()
    }


}

fun main(args: Array<String>) {
    SpringApplicationBuilder(CoursedbSpringApplication::class.java)
        .web(WebApplicationType.NONE)
        .run(*args)
}