package org.stpdiron.coursedbspring.services

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.CallbackQuery
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.relational.core.conversion.DbActionExecutionException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.stpdiron.coursedbspring.*
import org.stpdiron.coursedbspring.repos.*
import org.stpdiron.coursedbspring.services.handlers.UserCallbackHandler
import org.stpdiron.coursedbspring.services.handlers.UserMessageHandler
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepo: UserRepository,
    private val profileRepo: ProfileRepository,
    private val msgHandlers: Map<UserStateEnum, UserMessageHandler>,
    private val callbackHandlers: Map<UserStateEnum, UserCallbackHandler>
){
    private val logger = KotlinLogging.logger {}
    fun handleMessage(bot: Bot, message: Message) =
        try {
            message.from?.id?.let {
                logger.warn { "got user id $it" }
                userRepo.findByUserId(it)?.let { user ->
                    logger.warn { "got user $user" }
                    logger.warn { "now executing {${user.state}}" }
                    msgHandlers[user.state]?.handle(user, bot, message)
                }
            } .also {
                if (it == null) {
                    logger.error { "Unexpected user state or user not found" }
                }
            }
        } catch (e: Exception) {
            logger.error { e.message }
            bot.sendMessage(ChatId.fromId(message.chat.id), "Ошибка сервера")
        }

    fun handleCallback(bot: Bot, callback: CallbackQuery) =
        try {
            logger.warn { "got user id ${callback.from.id}" }
            userRepo.findByUserId(callback.from.id)?.let { user ->
                logger.warn { "got user $user" }
                logger.warn { "now executing {${user.state}}" }
                callbackHandlers[user.state]?.handle(user, bot, callback)
            }
        } catch (e: Exception) {
            logger.error { "${e::class}: ${e.message}" }
            bot.sendMessage(ChatId.fromId(callback.message!!.chat.id), "Ошибка сервера")
        }

    @Transactional
    fun createUserIfNew(userId: Long){
        try {
            val user = User(
                null,
                userId,
                UserStateEnum.SET_NAME,
                LocalDateTime.now(),
                false,
                0,0
            )
            val gotId = userRepo.save(user).id!!

            val profile = Profile(
                null, gotId, null, null, null,
                null, null, null,
                null, null, LocalDateTime.now()
            )
            profileRepo.save(profile)
        } catch (e : DbActionExecutionException) {
            logger.error { e.message }
        }

    }
}