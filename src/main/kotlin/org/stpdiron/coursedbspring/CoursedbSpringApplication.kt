package org.stpdiron.coursedbspring

import com.github.kotlintelegrambot.Bot
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import java.time.LocalDateTime

@SpringBootApplication
class CoursedbSpringApplication(private val bot: Bot, private val repo: ServiceUserRepo) : CommandLineRunner {
    private val logger = KotlinLogging.logger {}

    override fun run(vararg args: String?) {
        logger.info { "Hi!" }
        this.testRepo()
        bot.startPolling()
    }

    fun testRepo() {
        this.repo.save(ServiceUser(null, LocalDateTime.now(), true, 0, 0));
    }

}

fun main(args: Array<String>) {
    SpringApplicationBuilder(CoursedbSpringApplication::class.java)
        .web(WebApplicationType.NONE)
        .run(*args)
}