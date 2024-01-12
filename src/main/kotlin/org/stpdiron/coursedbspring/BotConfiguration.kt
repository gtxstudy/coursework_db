package org.stpdiron.coursedbspring

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.photos
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.logging.LogLevel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BotConfiguration {

    @Bean
    fun getBot() = bot {
        token = ""
        timeout = 30
        logLevel = LogLevel.Network.Body
        dispatch {
            command("start") {
                // create user and set initial state
            }
            text {
                // switch user state and use content of the message we got
                bot.sendMessage(ChatId.fromId(message.chat.id), text = text)
            }
            photos {
                // write photos in db
            }
        }
    }

}