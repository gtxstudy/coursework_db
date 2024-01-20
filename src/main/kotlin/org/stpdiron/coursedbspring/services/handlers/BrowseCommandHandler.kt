package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.TelegramFile
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.*
import org.stpdiron.coursedbspring.repos.*

@Component
class BrowseCommandHandler (
    private val userRepo: UserRepository,
    private val profileRepo: ProfileRepository,
    private val imageRepo: ImageRepository,
    private val cityRepo: CityRepository,
    private val fieldOfStudyRepo: FieldOfStudyRepository,
    private val universityRepo: UniversityRepository
): CommandHandler {
    override fun execute(user: User, bot: Bot, message: Message) {
        val recommendedUser: User? = userRepo.recommendUserById(user.id!!)
        println("$recommendedUser")
        if (recommendedUser == null)
            bot.sendMessage(ChatId.fromId(message.chat.id), "Никого не нашлось :(")
        else {
            profileRepo.findByUserId(recommendedUser.id!!).let { prof ->
                val city: City? = prof.city?.let { cityId -> cityRepo.findById(cityId) }?.get()
                val fieldOfStudy: FieldOfStudy? = prof.fieldOfStudyId?.let { fieldOfStudyId -> fieldOfStudyRepo.findById(fieldOfStudyId) }?.get()
                val university: University? = fieldOfStudy?.universityId?.let { universityId -> universityRepo.findById(universityId) }?.get()

                userRepo.save(user.copy(state = UserStateEnum.BROWSING))

                val buttons = ReactionType.entries.map { type ->
                    listOf(
                        InlineKeyboardButton.CallbackData(
                            text = type.toString(),
                            callbackData = "user:${recommendedUser.id},type:$type")
                    )
                }

                bot.sendPhoto(
                    chatId = ChatId.fromId(message.chat.id),
                    photo = imageRepo.findByProfileId(prof.id!!).map { img -> TelegramFile.ByFileId(img.tgId) }.first(),
                    caption = prof.getDescription(city, fieldOfStudy, university),
                    replyMarkup = InlineKeyboardMarkup.create(buttons)
                )
            }
        }
    }

    override fun commandType(): CommandEnum = CommandEnum.BROWSE
}