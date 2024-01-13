package org.stpdiron.coursedbspring.services

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.types.TelegramBotResult
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.stpdiron.coursedbspring.*
import org.stpdiron.coursedbspring.repos.*
import org.stpdiron.coursedbspring.services.handlers.SetNameHandler
import org.stpdiron.coursedbspring.services.handlers.UserStateHandler
import java.time.LocalDateTime

@Service
class UserService(
    private val userRepo: UserRepository,
    private val profileRepo: ProfileRepository,
    private val handlers: Map<UserStateEnum, UserStateHandler>
){
    private val logger = KotlinLogging.logger {}
    fun handleMessage(bot: Bot, message: Message) =
        message.from?.id?.let {
            logger.warn { "got user id $it" }
            userRepo.findByUserId(it)?.let { user ->
                logger.warn { "got user $user" }
                logger.warn { "now executing {${user.state}}" }
                handlers[user.state]?.handle(user, bot, message)
            }
        } .also {
            if (it == null) {
                logger.error { "Unexpected user state or user not found" }
            }
        }

    @Transactional
    fun createUserIfNew(userId: Long){
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
    }
}