# Usa uma imagem base do OpenJDK 17
FROM openjdk:17-jdk-slim

# Define um diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo JAR gerado pelo Maven para o container
COPY target/itub3-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão da aplicação
EXPOSE 8088

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
