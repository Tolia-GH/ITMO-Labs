package com.blps.lab3.config;

//@Configuration
//public class DataSourceConfig {
//    @Bean
//    public PoolingDataSource xaDataSource() {
//        PoolingDataSource dataSource = new PoolingDataSource();
//        dataSource.setClassName("org.postgresql.xa.PGXADataSource");
//        dataSource.setUniqueName("postgres");
//        dataSource.setMinPoolSize(5);
//        dataSource.setMaxPoolSize(20);
//        dataSource.setAllowLocalTransactions(true);
//
//        dataSource.getDriverProperties().put("user", "postgres");
//        dataSource.getDriverProperties().put("password", "123456");
//        dataSource.getDriverProperties().put("serverName", "localhost");
//        dataSource.getDriverProperties().put("portNumber", "5432");
//        dataSource.getDriverProperties().put("databaseName", "studs");
//
//        return dataSource;
//    }
//
//}
