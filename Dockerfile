FROM openjdk:17
EXPOSE 8080
ADD target/criptop2p.jar criptop2p.jar
ENTRYPOINT ["java","-jar","/criptop2p.jar"]