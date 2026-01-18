FROM quay.io/wildfly/wildfly:27.0.0.Final-jdk17

USER root
# Install curl
# RUN yum install -y curl && yum clean all

# Download Postgres Driver
RUN curl -L https://jdbc.postgresql.org/download/postgresql-42.7.2.jar -o /opt/jboss/wildfly/standalone/deployments/postgresql-42.7.2.jar

# Add admin user
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#007 --silent

USER jboss

# Copy CLI script
COPY setup-wildfly.cli /opt/jboss/wildfly/bin/setup-wildfly.cli

# Run CLI script to configure datasource
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/bin/setup-wildfly.cli

# Copy WAR
COPY target/Service1-0.1-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/Service1.war

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "-c", "standalone-full.xml"]
