FROM openjdk:8-alpine

# Copiando os arquivos
COPY . .
# Gerar o build
#RUN ./gradlew clean build

WORKDIR /build/libs

# set up non-root user (recommended for Heroku)
RUN adduser -D myuser
USER myuser

CMD ["java","-Dspring.profiles.active=production","-jar", "demo-0.0.1-SNAPSHOT.jar" ]
