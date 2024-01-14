package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.types.TelegramBotResult
import io.github.oshai.kotlinlogging.KotlinLogging
import org.stpdiron.coursedbspring.Profile
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.repos.ProfileRepository
import org.stpdiron.coursedbspring.repos.UserRepository


abstract class ChangeProfileHandler(
    protected val profileRepo: ProfileRepository,
    protected val userRepo: UserRepository
): UserMessageHandler {

    val logger = KotlinLogging.logger {}

    abstract fun mutateProfile(message: String, user: User, profile: Profile): Profile?

    abstract fun successMessage(bot: Bot, chatId: Long): TelegramBotResult<out Any>

    override fun handle(user: User, bot: Bot, message: Message): TelegramBotResult<out Any>  {
        return profileRepo.findByUserId(user.id!!).let { pr ->
            logger.warn { "Changing profile in progress" }
            message.text?.let { msg ->
                mutateProfile(msg, user, pr)?.let {
                    profileRepo.save(it)
                    userRepo.save(user.copy(state = nextState()))
                    successMessage(bot, message.chat.id)
                }
            }
        } ?: bot.sendMessage(
            ChatId.fromId(message.chat.id),
            "Ошибка, повторите попытку"
        )
    }

    abstract fun nextState(): UserStateEnum
}