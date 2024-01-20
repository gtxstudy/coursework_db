package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.CallbackQuery
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.types.TelegramBotResult
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.*
import org.stpdiron.coursedbspring.repos.ReactionRepository
import org.stpdiron.coursedbspring.services.UserService
import java.time.LocalDateTime

@Component
class BrowsingIncomingCallbackHandler (
    private val reactionRepo: ReactionRepository,
    private val browseIncomingCommandHandler: BrowseIncomingCommandHandler,
): UserCallbackHandler {
    private val prefix1: String = "user"
    private val prefix2: String = "type"

    override fun handle(user: User, bot: Bot, callback: CallbackQuery): TelegramBotResult<out Any> {
        val substrs = callback.data.split(',')
        if (substrs.size == 2) {
            if ((prefix1 in substrs[0]) && (prefix2 in substrs[1])) {
                val userId = substrs[0].substring(prefix1.length + 1).toLong()
                val type = ReactionType.valueOf(substrs[1].substring(prefix2.length + 1))
                reactionRepo.save(reactionRepo.findByFromIdAndAndToId(userId, user.id!!)!!.copy(seen = true))
                if (type == ReactionType.LIKE)
                    bot.sendMessage(
                        ChatId.fromId(callback.message!!.chat.id),
                        "Мэтч!"
                    )
                else
                    bot.sendMessage(
                        ChatId.fromId(callback.message!!.chat.id),
                        "Не мэтч"
                    )
                browseIncomingCommandHandler.execute(user, bot, callback.message!!)
                return bot.sendMessage(
                    ChatId.fromId(callback.message!!.chat.id),
                    ""
                )
            }
        }
        return bot.sendMessage(
            ChatId.fromId(callback.message!!.chat.id),
            "Ошибка сервера"
        )
    }

    override fun targetState() = UserStateEnum.BROWSING_INCOMING
}