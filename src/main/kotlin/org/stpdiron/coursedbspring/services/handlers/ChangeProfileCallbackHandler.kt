package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.CallbackQuery
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.types.TelegramBotResult
import io.github.oshai.kotlinlogging.KotlinLogging
import org.stpdiron.coursedbspring.Profile
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.repos.CityRepository
import org.stpdiron.coursedbspring.repos.ProfileRepository
import org.stpdiron.coursedbspring.repos.UserRepository

abstract class ChangeProfileCallbackHandler(
    protected val userRepo: UserRepository,
    protected val profileRepo: ProfileRepository
) : UserCallbackHandler{
    abstract val prefix: String
    val logger = KotlinLogging.logger {}

    abstract fun mutateProfile(data: String, user: User, profile: Profile): Profile?

    abstract fun successMessage(bot: Bot, profile: Profile, chatId: Long): TelegramBotResult<out Any>

    override fun handle(user: User, bot: Bot, callback: CallbackQuery): TelegramBotResult<out Any> =
        if ("$prefix:" in callback.data) {
            val data = callback.data.substring(prefix.length+1)
            logger.warn { data }
            val profile = profileRepo.findByUserId(user.id!!)
            mutateProfile(data, user, profile)?.let {
                profileRepo.save(it)
                user.state = nextState()
                userRepo.save(user)
                successMessage(bot, it, callback.message!!.chat.id)
            } ?: bot.sendMessage(
                ChatId.fromId(callback.message!!.chat.id),
                "Ошибка сервера"
            ).also { logger.error { "cant mutate profile" } }
        } else {
            bot.sendMessage(
                ChatId.fromId(callback.message!!.chat.id),
                "Ошибка сервера"
            )
        }

    abstract fun nextState(): UserStateEnum
}