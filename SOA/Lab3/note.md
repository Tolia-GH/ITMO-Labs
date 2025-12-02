

## 1 Change Deployment Service1 from Payara Server 6 to Wildfly:
1. Download Wildfly 29.0.1 (version 26.x.x does not support Java EE 9.1+)
2. Download Postgresql JDBC Driver (I used version 42.7)
3. Create directory in wildfly root directory(/modules/org/postgresql/main) and move Postgresql JDBC Driver jar file into it
4. Create module.xml file in the directory above like this:
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
    <module name="org.postgresql" xmlns="urn:jboss:module:1.9">
        <resources>
            <resource-root path="postgresql-42.7.8.jar"/>
        </resources>
    
        <dependencies>
            <module name="java.base"/>
            <module name="javax.api"/>
            <module name="javax.transaction.api"/>
        </dependencies>
    </module>
   ```
5. Start Wildfly server and visit Admin Console(localhost:9990)
6. Go to Configuration-Subsystems-Datasources&Drivers 
7. Add postgresql JDBC Driver
   - Name: postgresql
   - Driver Module Name: org.postgresql
   - Click Add button
8. Add Postgresql Datasource
   - Click Add Datasource
   - Choose Template: PostgreSQL
   - Set Attributes: 
     - Name: PostgreDS
     - JNDI Name: java:/jdbc/Lab2DS
   - Choose Added Postgresql JDBC Driver
   - Set Connection:
     - Set Connection URL: jdbc:postgresql://localhost:5432/studs
     - Set Username: postgres
     - Set Password: 123456
   - Test Connection: Successful!
   - Finish