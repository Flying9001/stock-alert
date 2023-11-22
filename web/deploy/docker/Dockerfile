FROM openjdk:8-jre
WORKDIR /app
COPY ./target/stock-alert.jar stock-alert.jar
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "-Dspring.config.location=/app/conf/", "-Dspring.profiles.active=test", "/app/stock-alert.jar", "-c"]
