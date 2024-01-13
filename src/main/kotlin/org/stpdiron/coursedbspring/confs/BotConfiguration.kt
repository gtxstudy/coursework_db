package org.stpdiron.coursedbspring.confs

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.photos
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.logging.LogLevel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.services.DataService
import java.time.LocalDateTime

@Configuration
class BotConfiguration(
    @Value("\${telegram.token}")
    private val token: String,
    @Autowired
    private val dataService: DataService,
){

    @Bean
    fun getBot() = bot {
        token = this@BotConfiguration.token
        timeout = 30
        logLevel = LogLevel.Network.Body

        dispatch {
            command("start") {
                // create user and set initial state
                bot.sendMessage(ChatId.fromId(message.chat.id), text = "123")
                val userId: Long = message.from?.id!!
                print(userId)
                var user: User? = dataService.userRepo.findByUserId(userId)
                if (user == null) {
                    user = User(null, userId, UserStateEnum.NEW, LocalDateTime.now(), true, 0, 0)
                    dataService.userRepo.save(user)
                }
                bot.sendMessage(ChatId.fromId(message.chat.id), text = "hi")
                println("555")
            }
            command("profile")
            {

            }
            command("update_profile")
            {

            }
            command("like")
            {

            }
            command("skip")
            {

            }
            /*
            text {
                // switch user state and use content of the message we got
                bot.sendMessage(ChatId.fromId(message.chat.id), text = text)
            }
            */
            photos {
                // write photos in db
            }
        }
    }

}