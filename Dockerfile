FROM openjdk:17.0.2-jdk as builder
RUN mkdir -p /app/source
COPY . /app/source
WORKDIR /app/source
RUN ./gradlew clean build


FROM openjdk:17.0.2-jdk-slim
COPY --from=builder /app/source/build/libs/application.jar /app/app.jar
EXPOSE 8080
CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar" ]

