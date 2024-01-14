package org.stpdiron.coursedbspring.repos

import org.springframework.data.repository.CrudRepository
import org.stpdiron.coursedbspring.City

interface CityRepository: CrudRepository<City, Long> {
    fun findCityByName(name: String): City?
}