#!/bin/sh
update-ca-certificates
envsubst < conf/application-temp.yml > conf/application.yml

java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,suspend=n \
    -Xms512m \
    -Xmx$JVM_XMX \
    -Dfile.encoding=UTF-8 \
    -Duser.timezone=Asia/Ho_Chi_Minh \
    -Duser.language=en \
    -Duser.country=US \
    -Djava.io.tmpdir=/tmp \
    -Dspring.profiles.active=$ENV \
    -Dspring.config.location=conf/ \
    -jar app.jar