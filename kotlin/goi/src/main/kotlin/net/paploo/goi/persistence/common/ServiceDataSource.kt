package net.paploo.goi.persistence.common

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import java.io.Closeable
import javax.sql.DataSource

interface ServiceDataSource : DataSource, Closeable {

    //⚠️ WARNING: Always pass the DataSource, never the Connection!!! If not, jooq will cause deadlocking!
    fun dsl(): DSLContext = DSL.using(this, SQLDialect.POSTGRES)

}

class HikariServicedataSource(
    val hikariDataSource: HikariDataSource
) : ServiceDataSource, DataSource by hikariDataSource, Closeable by hikariDataSource {

    constructor(configurationBlock: HikariConfig.() -> Unit) : this(
        HikariConfig().apply(configurationBlock)
    )

    constructor(configuration: HikariConfig) : this(
        HikariDataSource(configuration)
    )

}