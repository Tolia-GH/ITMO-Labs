

## 1 Change Service1 Deployment from Payara Server 6 to Wildfly 29:

### 1 Configure Wildfly JDBC Driver & Datasource
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

### 2 Configure HTTPS on wildfly server
1. just open /standalone/configuration/standalone.xml and edit as follows:
   ```xml
   <socket-binding name="https" port="8181"/>
   ```
2. Wildfly will auto generate self-signed certificate so we don't need to use keystore to generate ourselves
3. Open terminal in wildfly home directory and run:
   ```cmd
   keytool -exportcert -alias server -keystore application.keystore -file service1.crt
   ```
   Default password is: password  
   Then run:  
   ```
   keytool -importcert -alias service1 -file service1.crt -keystore service1-truststore.p12 -storetype PKCS12 -storepass changeit
   ```
   if appears `Trust this certificate? [no]:`, enter `y`  
   now put the service1-truststore.p12 into Service2/src/resources/service1-truststore.p12
3. Restart wildfly server, and now Service1 will run on https://localhost:8181