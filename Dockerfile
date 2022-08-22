FROM openjdk:17.0.2-jdk as builder
RUN mkdir -p /app/source
COPY . /app/source
WORKDIR /app/source
RUN ./gradlew clean build


FROM openjdk:17.0.2-jdk-slim
COPY --from=builder /app/source/build/libs/application.jar /app/app.jar
CMD java -Dserver.port=$PORT $JAVA_OPTS -jar /app/app.jar

