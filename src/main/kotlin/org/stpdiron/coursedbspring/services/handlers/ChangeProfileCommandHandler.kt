package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.CommandEnum
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.repos.UserRepository

@Component
class ChangeProfileCommandHandler (
    private val userRepo: UserRepository
): CommandHandler {
    private val logger = KotlinLogging.logger {}

    override fun execute(user: User, bot: Bot, message: Message) {
        try {
            logger.warn { "got user id ${message.from?.id}" }
            userRepo.findByUserId(message.from!!.id)?.let { user ->
                logger.warn { "got user $user" }
                logger.warn { "now executing {${user.state}}" }
                userRepo.save(user.copy(state = UserStateEnum.SET_NAME))
                bot.sendMessage(ChatId.fromId(message.chat.id), text = "Введите ваше имя")
            }
        } catch (e: Exception) {
            logger.error { e.message }
            bot.sendMessage(ChatId.fromId(message.chat.id), "Ошибка сервера")
        }
    }

    override fun commandType() = CommandEnum.CHANGE_PROFILE
}