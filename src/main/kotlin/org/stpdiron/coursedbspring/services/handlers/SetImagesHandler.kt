package org.stpdiron.coursedbspring.services.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.types.TelegramBotResult
import org.springframework.stereotype.Component
import org.stpdiron.coursedbspring.Image
import org.stpdiron.coursedbspring.Profile
import org.stpdiron.coursedbspring.User
import org.stpdiron.coursedbspring.UserStateEnum
import org.stpdiron.coursedbspring.repos.CityRepository
import org.stpdiron.coursedbspring.repos.ImageRepository
import org.stpdiron.coursedbspring.repos.ProfileRepository
import org.stpdiron.coursedbspring.repos.UserRepository
import java.time.LocalDateTime

@Component
class SetImagesHandler (
    profileRepo: ProfileRepository,
    userRepo: UserRepository,
    private val imageRepository: ImageRepository
) : ChangeProfileHandler(profileRepo, userRepo) {

    override fun mutateProfile(message: Message, user: User, profile: Profile): Profile = profile.also {
        this.imageRepository.deleteByProfileId(profile.id!!)
        message.photo?.first {
            this.imageRepository.save(Image(null, profile.id, it.fileId, LocalDateTime.now()));
            true
        }
    }

    override fun successMessage(bot: Bot, chatId: Long) =
        bot.sendMessage(
            ChatId.fromId(chatId),
            "Введите описание для профиля"
        )

    override fun targetState(): UserStateEnum = UserStateEnum.SET_IMAGES

    override fun nextState(): UserStateEnum = UserStateEnum.SET_DESCRIPTION
}