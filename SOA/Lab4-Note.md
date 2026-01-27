
1. SOA Lab4 should be developped base on Lab2(Not based on Lab3!)
2. Need 2-3 helios account of student on helios to deploy Lab4
3. For each account deploy 1 instance of Service1 and Service2, make sure different service instance use different resources and different database on helios
4. Change Service1 from Restful to SOAP, Service2 just keep on Restful
5. Use Mule ESB to integrate 2 service together
6. Realize an additional Rest Layer(on Mule ESB, don't need to create another Rest API Service), so that I don't need to change the client (Service 2) to access to Service 1, the Rest Layer should not contain any logic besides calling SOAP Services 

```mermaid
flowchart TB
 subgraph Client["Client"]
        A["React Frontend"]
  end
 subgraph SpringBoot["SpringBoot"]
        B["Service2<br>Spring Boot<br>REST API"]
  end
 subgraph Mule["Mule ESB"]
        C["REST Layer<br>HTTP Listener"]
        D["Translator<br>DataWeave"]
        E["SOAP Layer<br>Web Service Consumer"]
  end
 subgraph WildFly["WildFly"]
        F["Service1<br>SOAP Web Service"]
  end
    A -- REST / XML --> B & C
    B -- REST / XML --> C & A
    C --> D
    D --> E & C
    E -- SOAP / XML --> F
    F -- SOAP / XML --> E
    E --> D
    C -- REST / XML --> B & A
```

Now ther should be two way for React Frontend to call Service1 and Service2:

1. React Frontend <-rest-> Service2 <-rest-> Mule <-soap-> Service1
2. React Frontend <-rest-> Mule <-soap-> Service1

```mermaid
flowchart TB
 subgraph Client["Client"]
        A["React Frontend"]
  end
 subgraph HAProxy2["HAProxy Service2"]
        A1["HAProxy LB"]
  end
 subgraph Service2["Service2 Instances"]
        B1["Instance 1 on user 1"]
        B2["Instance 2 on user 2"]
  end
 subgraph Mule["Mule ESB"]
        C1["REST Layer"]
        C2["Translator"]
        C3["SOAP Layer"]
  end
 subgraph HAProxy1["HAProxy Service1"]
        D1["HAProxy LB"]
  end
 subgraph Service1["Service1 Instances"]
        F1["Instance 1 on user 1"]
        F2["Instance 2 on user 2"]
  end
    A -- REST / XML --> C1
    A <-- REST / XML --> A1
    A1 <-- REST / XML --> B1 & B2
    B1 <-- REST / XML --> C1
    B2 <-- REST / XML --> C1
    C1 <--> C2
    C1 -- REST / XML --> A
    C2 <--> C3
    C3 <-- SOAP / XML --> D1
    D1 <--> F1 & F2
```

```xml
                <datasource jndi-name="java:/jdbc/Lab2DS" pool-name="Lab2DS">
                    <connection-url>jdbc:postgresql://localhost:5432/studs</connection-url>
                    <driver-class>org.postgresql.Driver</driver-class>
                    <driver>org.postgresql</driver>
                    <security>
                        <user-name>postgres</user-name>
                        <password>123456</password>
                    </security>
                    <validation>
                        <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
                        <validate-on-match>true</validate-on-match>
                        <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter"/>
                    </validation>
                </datasource>
```