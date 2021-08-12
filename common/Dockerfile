FROM webdevops/liquibase:mysql

COPY ./src/main/resources/db /liquibase/db

COPY docker/liquibase/bin/entrypoint.sh /liquibase/bin/entrypoint.sh

RUN chmod a+x /liquibase/bin/entrypoint.sh \
    && wget -q "https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.18/mysql-connector-java-8.0.18.jar" -O /liquibase/mysql-connector-java.jar

ENTRYPOINT ["bin/entrypoint.sh"]