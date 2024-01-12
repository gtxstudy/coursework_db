package org.stpdiron.coursedbspring.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class DatabaseInitService(
    private val datasource: DataSource

) {
    fun init() {
        val populator = ResourceDatabasePopulator()
        populator.addScripts(
            ClassPathResource("script.sql")
        )
        populator.execute(datasource)
    }
}