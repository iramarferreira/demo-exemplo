FROM postgres

## Configurando o banco de dados
RUN --name some-postgres \
    -e POSTGRES_DB=$POSTGRES_DB \
    -e POSTGRES_USER=$POSTGRES_USER \
    -e POSTGRES_PASSWORD=$POSTGRES_PASSWORD \
    -d postgres


FROM openjdk:8-alpine
# adicionando usuário para poder executar

#COPY ./build/libs/*.jar /
# Copiando os arquivos
COPY . .
# Gerar o build
RUN ./gradlew build

# Não funciona do heroku
# EXPOSE $PORT

WORKDIR /build/libs


# set up non-root user (recommended for Heroku)
RUN adduser -D myuser
USER myuser

#ENTRYPOINT ["java", "-Dspring.profiles.active=dev"]

#ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar","Exemplo-0.0.1-SNAPSHOT.jar" ]
#CMD java $JAVA_OPTS -Dserver.port=$PORT -Dspring.profiles.active=production -jar Exemplo-0.0.1-SNAPSHOT.jar
CMD ["java","-Dspring.profiles.active=production","-jar", "demo-0.0.1-SNAPSHOT.jar" ]