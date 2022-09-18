FROM openjdk:17
EXPOSE 8080
ADD build/libs/*.war criptop2p.war
ENTRYPOINT ["java","-jar","/criptop2p.war"]