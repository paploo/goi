package net.paploo.goi.persistence.common

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.impl.DSL
import java.io.Closeable
import javax.sql.DataSource

interface ServiceDataSource : DataSource, Closeable {

    fun dsl(): DSLContext = DSL.using(this.connection)

}

class HikariServicedataSource(
    val hikariDataSource: HikariDataSource
) : ServiceDataSource, DataSource by hikariDataSource, Closeable by hikariDataSource {

    constructor(configuration: HikariConfig.() -> Unit) : this(
        HikariConfig().apply(configuration).let { HikariDataSource(it) }
    )

}