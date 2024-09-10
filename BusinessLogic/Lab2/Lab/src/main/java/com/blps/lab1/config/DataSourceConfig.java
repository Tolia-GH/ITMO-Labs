package com.blps.lab1.config;

import bitronix.tm.resource.jdbc.PoolingDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource xaDataSource() {
        PoolingDataSource dataSource = new PoolingDataSource();
        dataSource.setClassName("org.postgresql.xa.PGXADataSource");
        dataSource.setUniqueName("postgres");
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(20);
        dataSource.setAllowLocalTransactions(true);

        dataSource.getDriverProperties().put("user", "postgres");
        dataSource.getDriverProperties().put("password", "123456");
        dataSource.getDriverProperties().put("serverName", "localhost");
        dataSource.getDriverProperties().put("portNumber", "5432");
        dataSource.getDriverProperties().put("databaseName", "studs");

        return dataSource;
    }

}
