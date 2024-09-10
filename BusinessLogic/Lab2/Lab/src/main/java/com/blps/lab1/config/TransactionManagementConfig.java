package com.blps.lab1.config;

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(transactionManagerRef = "transactionManager", basePackages = "com.blps.lab1.databaseJPA")
public class TransactionManagementConfig {

    @Bean(name = "transactionManager")
    public JtaTransactionManager transactionManager() {
        JtaTransactionManager transactionManager = new JtaTransactionManager();
        transactionManager.setTransactionManager(TransactionManagerServices.getTransactionManager());
        return transactionManager;
    }
}
