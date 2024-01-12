package org.stpdiron.coursedbspring

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.data.repository.CrudRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.TransactionManager
import java.time.LocalDateTime
import javax.sql.DataSource

@Configuration
@EnableJdbcRepositories
class DataConfiguration(
    @Value("\${postgres.url}")
    private val url: String,
    @Value("\${postgres.username}")
    private val username: String,
    @Value("\${postgres.password}")
    private val password: String,
): AbstractJdbcConfiguration() {
    @Bean
    fun dataSource(): DataSource = DataSourceBuilder
        .create()
        .driverClassName("org.postgresql.Driver")
        .url(this.url)
        .username(this.username)
        .password(this.password)
        .build();

    @Bean
    fun namedParameterJdbcOperations(dataSource: DataSource): NamedParameterJdbcOperations = NamedParameterJdbcTemplate(dataSource);

    @Bean
    fun transactionManager(dataSource: DataSource): TransactionManager = DataSourceTransactionManager(dataSource);
}

class ServiceUser(
    @Id
    val id: Long?,
    val created: LocalDateTime,
    val active: Boolean,
    val reactionsFrom: Long,
    val reactionsTo: Long
);

interface ServiceUserRepo: CrudRepository<ServiceUser, Long>;