package org.stpdiron.coursedbspring.confs

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.TransactionManager
import javax.sql.DataSource

@Configuration
class DataSourceConfiguration(
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
        .url(url)
        .username(username)
        .password(password)
        .build();

    @Bean
    fun namedParameterJdbcOperations(dataSource: DataSource): NamedParameterJdbcOperations = NamedParameterJdbcTemplate(dataSource);

    @Bean
    fun transactionManager(dataSource: DataSource): TransactionManager = DataSourceTransactionManager(dataSource);
}