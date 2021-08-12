FROM openjdk:8-jre-alpine

RUN apk add --update \
    curl \
    openssl \
    tzdata \
    gettext \
    openssl \
    && rm -rf /var/cache/apk/*

# Set location of the main project
ENV APP_HOME /usr/app
ENV JVM_XMX 512m

EXPOSE 9090

# Copy your fat jar to the container
COPY target/*.jar $APP_HOME/app.jar
COPY src/main/resources/* $APP_HOME/conf/

COPY bin/entrypoint.sh $APP_HOME/bin/entrypoint.sh
RUN chmod a+x $APP_HOME/bin/entrypoint.sh

#add data for timezone if not we can not get correct date time
RUN apk add --no-cache tzdata curl
ENV TZ Asia/Ho_Chi_Minh

# Launch the verticle
WORKDIR $APP_HOME

ENTRYPOINT ["bin/entrypoint.sh"]
