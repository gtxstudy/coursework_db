package org.stpdiron.coursedbspring.confs

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.*
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.TelegramFile
import com.github.kotlintelegrambot.logging.LogLevel
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.stpdiron.coursedbspring.services.UserService

@Configuration
class BotConfiguration(
    @Value("\${telegram.token}")
    private val token: String,
    @Autowired
    private val userService: UserService,
){
    private val logger = KotlinLogging.logger {}
    var mediaId : String? = null

    @Bean
    fun getBot() = bot {
        token = this@BotConfiguration.token
        timeout = 30
        logLevel = LogLevel.Network.Body

        dispatch {
            command("start") {
                message.from?.id?.let {
                    userService.createUserIfNew(it)
                    bot.sendMessage(ChatId.fromId(message.chat.id), text = "Введите ваше имя")
                }
            }
            text {
                logger.info { "got text: <${message.text}>" }
                if (message.text!![0] != '/')
                    userService.handleMessage(bot, message)
            }
            callbackQuery {
                userService.handleCallback(bot, callbackQuery)
            }
            photos {
                this.media.firstOrNull()?.let {
                    mediaId = it.fileId
                }
            }
            command("lastPhoto") {
                mediaId?.let {
                    bot.sendPhoto(
                        chatId = ChatId.fromId(message.chat.id),
                        photo = TelegramFile.ByFileId(it),
                        caption = "WOW!"
                    )
                } ?: bot.sendMessage(ChatId.fromId(message.chat.id), "nothing to send")
            }
        }
    }

}