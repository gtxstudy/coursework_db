package org.stpdiron.coursedbspring.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.stpdiron.coursedbspring.*
import org.stpdiron.coursedbspring.repos.*

@Service
class DataService(
    val userRepo: UserRepository,
    val profileRepo: ProfileRepository,
    val reactionRepo: ReactionRepository,
    val imageRepo: ImageRepository,
    val universityRepo: UniversityRepository,
    val facultyRepo: FacultyRepository,
    val fieldOfStudyRepo: FieldOfStudyRepository,
){
    fun userGetProfile(user: User): Profile? {
        return user.id?.let { profileRepo.findByUserId(it) }
    }

    fun userGetReactionsTo(user: User): List<Reaction>? {
        return user.id?.let { reactionRepo.findByToId(it) }
    }

    fun userGetReactionsFrom(user: User): List<Reaction>? {
        return user.id?.let { reactionRepo.findByFromId(it) }
    }

    fun profileGetImages(profile: Profile): List<Image>? {
        return profile.id?.let { imageRepo.findByProfileId(it) }
    }

    fun universityGetFaculties(university: University): List<Faculty>? {
        return university.id?.let { facultyRepo.findByUniversityId(it) }
    }

    fun facultyGetFieldsOfStudy(faculty: Faculty): List<FieldOfStudy>? {
        return faculty.id?.let { fieldOfStudyRepo.findByFacultyId(it) }
    }
}