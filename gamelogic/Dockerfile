FROM maven:3.8.1-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#FROM openjdk:11-jre-slim
#COPY --from=build /home/app/target/gameserver-0.0.1-jar-with-dependencies.jar /usr/local/lib/gameserver.jar
#ENV MATCHMAKER_HOST="matchmaker-service" MATCHMAKER_PORT="80" REDIS_HOST="redis-service" REDIS_PORT="6379"
#EXPOSE 8765
#EXPOSE 8888
#ENTRYPOINT ["java","-jar","/usr/local/lib/gameserver.jar"]