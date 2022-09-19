FROM openjdk:17
EXPOSE 8080
ARG APP_NAME="criptop2p"
ARG APP_VERSION='0.0.1-SNAPSHOT'
ARG WAR_FILE="build/libs/${APP_NAME}-${APP_VERSION}.war}"
##ADD build/libs/*.war criptop2p.war
COPY ${WAR_FILE} criptop2p.war
##ENTRYPOINT ["java","-jar","/criptop2p.war"]