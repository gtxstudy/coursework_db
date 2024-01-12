package org.stpdiron.coursedbspring

import com.github.kotlintelegrambot.Bot
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.stpdiron.coursedbspring.repos.UserRepo
import org.stpdiron.coursedbspring.services.DatabaseInitService
import java.time.LocalDateTime

@SpringBootApplication
class CoursedbSpringApplication(
    private val bot: Bot,
    private val repo: UserRepo,
    private val initService: DatabaseInitService
) : CommandLineRunner {
    private val logger = KotlinLogging.logger {}

    override fun run(vararg args: String?) {
        logger.info { "Hi!" }
        testRepo()
        initService.init()
        bot.startPolling()
    }

    fun testRepo() {
        repo.save(User(null, LocalDateTime.now(), true, 0, 0));
    }

}

fun main(args: Array<String>) {
    SpringApplicationBuilder(CoursedbSpringApplication::class.java)
        .web(WebApplicationType.NONE)
        .run(*args)
}