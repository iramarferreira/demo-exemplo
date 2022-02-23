FROM openjdk:8-alpine
#MAINTAINER Iramar Ferreira "iramar.ferreira@ifrn.edu.br"
#VOLUME /temp
#EXPOSE 8080
#ENV JAVA_OPTS=""
#RUN adduser -D springboot
#ADD --chown=springboot build/libs/*.jar /home/springboot/app.jar
#ENTRYPOINT exec java $JAVA_OPTS -jar /home/springboot/app.jar
##ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar","Exemplo-0.0.1-SNAPSHOT.jar" ]
#
# adicionando usuário para poder executar

#COPY ./build/libs/*.jar /
# Copiando os arquivos
COPY . .
# Gerar o build
#RUN ./gradlew clean build

# Não funciona do heroku
# EXPOSE $PORT

WORKDIR /build/libs


# set up non-root user (recommended for Heroku)
RUN adduser -D myuser
USER myuser

#ENTRYPOINT ["java", "-Dspring.profiles.active=dev"]

#ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar","Exemplo-0.0.1-SNAPSHOT.jar" ]
#CMD java $JAVA_OPTS -Dserver.port=$PORT -Dspring.profiles.active=production -jar Exemplo-0.0.1-SNAPSHOT.jar
#CMD ["java","-Dspring.profiles.active=production","-jar", "demo-0.0.1-SNAPSHOT.jar" ]

CMD ["java","-Dspring.profiles.active=production","-jar", "demo-0.0.1-SNAPSHOT.jar" ]
#CMD ["java","-Dspring.profiles.active=production","-Dspring.datasource.url=jdbc:postgresql://ec2-54-157-160-218.compute-1.amazonaws.com:5432/dcblbsm8gu76rp", "-Dspring.datasource.username=${DB_USER}","-Dspring.datasource.password=${DB_PASS}" ,"-jar", "demo-0.0.1-SNAPSHOT.jar" ]