package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.TelegramFile
import com.github.kotlintelegrambot.entities.inputmedia.GroupableMedia
import com.github.kotlintelegrambot.entities.inputmedia.InputMediaPhoto
import com.github.kotlintelegrambot.entities.inputmedia.MediaGroup
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.*
import org.stpdiron.coursedbspring.repos.*

@Component
class ShowProfileCommandHandler(
    private val profileRepo: ProfileRepository,
    private val imageRepo: ImageRepository,
    private val cityRepo: CityRepository,
    private val fieldOfStudyRepo: FieldOfStudyRepository,
    private val universityRepo: UniversityRepository
): CommandHandler {
    override fun execute(user: User, bot: Bot, message: Message) {
        profileRepo.findByUserId(user.id!!).let { prof ->
            val city: City? = prof.city?.let { cityId -> cityRepo.findById(cityId) }?.get()
            val fieldOfStudy: FieldOfStudy? = prof.fieldOfStudyId?.let { fieldOfStudyId -> fieldOfStudyRepo.findById(fieldOfStudyId) }?.get()
            val university: University? = fieldOfStudy?.universityId?.let { universityId -> universityRepo.findById(universityId) }?.get()

            bot.sendPhoto(
                chatId = ChatId.fromId(message.chat.id),
                photo = imageRepo.findByProfileId(prof.id!!).map { img -> TelegramFile.ByFileId(img.tgId) }.first(),
                caption = prof.getDescription(city, fieldOfStudy, university)
            )
        }
    }

    override fun commandType() = CommandEnum.SHOW_PROFILE
}