package org.stpdiron.coursedbspring.repos

import org.springframework.data.repository.CrudRepository
import org.stpdiron.coursedbspring.Image

interface ImageRepository: CrudRepository<Image, Long> {
    fun findByProfileId(profileId: Long): List<Image>;
}