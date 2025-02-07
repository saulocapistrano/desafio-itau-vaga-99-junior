## **📜 README.md - Transaction API**

# **Transaction API - ITUB3**

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen.svg)
![Docker](https://img.shields.io/badge/Docker-Supported-blue)
![Swagger](https://img.shields.io/badge/Swagger-Enabled-orange)
![Maven](https://img.shields.io/badge/Maven-Build-blue)

**Transaction API** is a RESTful API designed for managing financial transactions and providing statistical insights. The application is built using **Spring Boot**, ensuring high scalability, security, and maintainability. It supports **observability** via **Spring Actuator & Prometheus**, **security** with **Spring Security**, and is fully **containerized using Docker**.

---

## **📌 Features**
- ✅ **Transaction Management**: Create and delete financial transactions.
- ✅ **Statistics Calculation**: Provides aggregated statistics for transactions.
- ✅ **Health Check**: Ensures system availability.
- ✅ **Configuration Management**: Allows dynamic adjustments of statistics window.
- ✅ **Security**: Uses Spring Security for API protection.
- ✅ **Rate Limiting**: Implements request throttling using Bucket4J.
- ✅ **Observability**: Exposes monitoring endpoints via Actuator & Prometheus.
- ✅ **Swagger Documentation**: Provides interactive API documentation.

---

## **📂 Project Structure**
```
📦 itub3
 ┣ 📂 src/main/java/com/itub/itub3
 ┃ ┣ 📂 application
 ┃ ┃ ┣ 📂 dto  # Data Transfer Objects (DTOs)
 ┃ ┃ ┃ ┣ TransactionRequestDTO.java
 ┃ ┃ ┃ ┗ StatisticsResponseDTO.java
 ┃ ┃ ┗ 📂 service  # Business Logic Layer
 ┃ ┃ ┃ ┣ TransactionService.java
 ┃ ┃ ┃ ┗ ConfigurationService.java
 ┃ ┣ 📂 infrastructure
 ┃ ┃ ┣ 📂 config  # Configuration Classes
 ┃ ┃ ┃ ┣ CorsConfig.java
 ┃ ┃ ┃ ┣ SwaggerConfig.java
 ┃ ┃ ┃ ┗ SecurityConfig.java
 ┃ ┃ ┣ 📂 security  # Security and Rate Limiting
 ┃ ┃ ┃ ┣ RateLimitingFilter.java
 ┃ ┃ ┃ ┗ SecurityConfig.java
 ┃ ┣ 📂 interfaces
 ┃ ┃ ┗ 📂 controller  # API Controllers
 ┃ ┃ ┃ ┣ HomeController.java
 ┃ ┃ ┃ ┣ TransactionController.java
 ┃ ┃ ┃ ┣ ConfigurationController.java
 ┃ ┃ ┃ ┗ HealthCheckController.java
 ┣ 📂 src/main/resources
 ┃ ┣ application.properties
 ┃ ┗ logback-spring.xml
 ┣ 📜 Dockerfile
 ┣ 📜 pom.xml
 ┗ 📜 README.md
```

---

## **📌 API Controllers**
### **1️⃣ HomeController**
- **`GET /`** → Returns a basic success message: `"API Itub3 successfully running!"`

### **2️⃣ TransactionController**
- **`POST /transactions`** → Creates a new transaction.
- **`DELETE /transactions`** → Deletes all transactions.
- **`GET /statistics`** → Retrieves statistical data based on stored transactions.

### **3️⃣ ConfigurationController**
- **`PATCH /config/statistics-window?seconds={value}`** → Updates the statistics window size.
- **`GET /config/statistics-window`** → Returns the current statistics window size.

### **4️⃣ HealthCheckController**
- **`GET /health`** → Returns `"API is running"` to verify service availability.

---

## **📌 Services**
### **TransactionService**
- **Registers** transactions.
- **Calculates** statistics like sum, average, max, min, and count.
- **Handles** transaction deletions.

### **ConfigurationService**
- **Manages** statistics window time dynamically.

---

## **📌 Configuration Files**
### **1️⃣ `application.properties`**
Defines:
- Server port
- Logging levels
- Observability configurations
- Security & application settings

### **2️⃣ `logback-spring.xml`**
Handles **log formatting** and levels for different environments.

### **3️⃣ `SwaggerConfig.java`**
Configures **Swagger OpenAPI documentation**.

### **4️⃣ `SecurityConfig.java`**
Manages **Spring Security**, including:
- Disabling CSRF for APIs.
- Allowing unrestricted access to Swagger endpoints.

### **5️⃣ `CorsConfig.java`**
Configures **CORS settings** to allow cross-origin requests.

---

## **📌 Observability & Logging**
- **Spring Boot Actuator** provides:
    - `/actuator/health` → System health check.
    - `/actuator/metrics` → Application metrics.
    - `/actuator/prometheus` → Metrics in Prometheus format.

---

## **📌 API Documentation (Swagger)**
- **Access Swagger UI:** [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)
- **API JSON Documentation:** [`http://localhost:8080/v3/api-docs`](http://localhost:8080/v3/api-docs)

---

## **📌 Containerization (Docker)**
### **1️⃣ Dockerfile**
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/itub3-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080
```

### **2️⃣ Build & Run**
```sh
# Build the project
mvn clean package -DskipTests

# Build the Docker image
docker build -t itub3-app .

# Run the container
docker run -d -p 8080:8080 --name itub3-container itub3-app
```

### **3️⃣ Stop & Remove Container**
```sh
docker stop itub3-container
docker rm itub3-container
docker rmi itub3-app
```

---

## **📌 How to Clone and Run Locally**
### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/YOUR_USERNAME/itub3.git
cd itub3
```

### **2️⃣ Build the Project**
```sh
mvn clean install -DskipTests
```

### **3️⃣ Run the Application**
```sh
mvn spring-boot:run
```

### **4️⃣ Run in Docker**
```sh
docker build -t itub3-app .
docker run -d -p 8080:8080 --name itub3-container itub3-app
```

---

## **📌 Testing**
### **Run Unit Tests**
```sh
mvn test
```

---

## **📌 Author & License**
👤 **Saulo Capistrano**  
📧 Contact: `saulocapistrano@gmail.com`  
📄 License: MIT License

---

**🚀 Ready to use the API? Start exploring with Swagger!**

[`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)



## **📜 README.md - Transaction API**

# **Transaction API - ITUB3**

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen.svg)
![Docker](https://img.shields.io/badge/Docker-Suportado-blue)
![Swagger](https://img.shields.io/badge/Swagger-Habilitado-orange)
![Maven](https://img.shields.io/badge/Maven-Build-blue)

A **Transaction API** é uma API RESTful projetada para gerenciar transações financeiras e fornecer estatísticas agregadas. A aplicação foi desenvolvida utilizando **Spring Boot**, garantindo **escalabilidade, segurança e fácil manutenção**.

Ela suporta **observabilidade** via **Spring Actuator & Prometheus**, possui **segurança** com **Spring Security**, e está **totalmente conteinerizada com Docker**.

---

## **📌 Recursos**
- ✅ **Gerenciamento de Transações**: Criar e excluir transações financeiras.
- ✅ **Cálculo de Estatísticas**: Obtém dados agregados das transações.
- ✅ **Verificação de Saúde (Health Check)**: Garante a disponibilidade do sistema.
- ✅ **Gerenciamento de Configuração**: Permite ajustes dinâmicos no tempo da janela estatística.
- ✅ **Segurança**: Proteção da API com Spring Security.
- ✅ **Rate Limiting**: Implementação de controle de requisições com Bucket4J.
- ✅ **Observabilidade**: Exposição de métricas via Actuator & Prometheus.
- ✅ **Documentação Swagger**: API documentada de forma interativa.

---

## **📂 Estrutura do Projeto**
```
📦 itub3
 ┣ 📂 src/main/java/com/itub/itub3
 ┃ ┣ 📂 application
 ┃ ┃ ┣ 📂 dto  # Objetos de Transferência de Dados (DTOs)
 ┃ ┃ ┃ ┣ TransactionRequestDTO.java
 ┃ ┃ ┃ ┗ StatisticsResponseDTO.java
 ┃ ┃ ┗ 📂 service  # Camada de Lógica de Negócio
 ┃ ┃ ┃ ┣ TransactionService.java
 ┃ ┃ ┃ ┗ ConfigurationService.java
 ┃ ┣ 📂 infrastructure
 ┃ ┃ ┣ 📂 config  # Configurações da Aplicação
 ┃ ┃ ┃ ┣ CorsConfig.java
 ┃ ┃ ┃ ┣ SwaggerConfig.java
 ┃ ┃ ┃ ┗ SecurityConfig.java
 ┃ ┃ ┣ 📂 security  # Segurança e Rate Limiting
 ┃ ┃ ┃ ┣ RateLimitingFilter.java
 ┃ ┃ ┃ ┗ SecurityConfig.java
 ┃ ┣ 📂 interfaces
 ┃ ┃ ┗ 📂 controller  # Controladores da API
 ┃ ┃ ┃ ┣ HomeController.java
 ┃ ┃ ┃ ┣ TransactionController.java
 ┃ ┃ ┃ ┣ ConfigurationController.java
 ┃ ┃ ┃ ┗ HealthCheckController.java
 ┣ 📂 src/main/resources
 ┃ ┣ application.properties
 ┃ ┗ logback-spring.xml
 ┣ 📜 Dockerfile
 ┣ 📜 pom.xml
 ┗ 📜 README.md
```

---

## **📌 Controladores da API**
### **1️⃣ HomeController**
- **`GET /`** → Retorna uma mensagem básica de sucesso: `"API Itub3 executando com sucesso!"`

### **2️⃣ TransactionController**
- **`POST /transactions`** → Cria uma nova transação.
- **`DELETE /transactions`** → Exclui todas as transações.
- **`GET /statistics`** → Obtém estatísticas agregadas das transações.

### **3️⃣ ConfigurationController**
- **`PATCH /config/statistics-window?seconds={value}`** → Atualiza o tempo da janela estatística.
- **`GET /config/statistics-window`** → Retorna o tempo atual da janela estatística.

### **4️⃣ HealthCheckController**
- **`GET /health`** → Retorna `"API is running"` para verificar a disponibilidade do serviço.

---

## **📌 Serviços**
### **TransactionService**
- **Registra** transações.
- **Calcula** estatísticas como soma, média, máximo, mínimo e contagem.
- **Gerencia** exclusão de transações.

### **ConfigurationService**
- **Gerencia** dinamicamente o tempo da janela estatística.

---

## **📌 Arquivos de Configuração**
### **1️⃣ `application.properties`**
Define:
- Porta do servidor
- Níveis de log
- Configuração de observabilidade
- Segurança e configurações da aplicação

### **2️⃣ `logback-spring.xml`**
Gerencia **formatação e níveis de log** para diferentes ambientes.

### **3️⃣ `SwaggerConfig.java`**
Configura a **documentação OpenAPI (Swagger)**.

### **4️⃣ `SecurityConfig.java`**
Gerencia **segurança** no Spring Security:
- Desabilita CSRF para APIs.
- Permite acesso irrestrito aos endpoints do Swagger.

### **5️⃣ `CorsConfig.java`**
Define **política de CORS** para permitir requisições de diferentes origens.

---

## **📌 Observabilidade & Logging**
- **Spring Boot Actuator** fornece:
    - `/actuator/health` → Verificação de integridade do sistema.
    - `/actuator/metrics` → Métricas da aplicação.
    - `/actuator/prometheus` → Métricas no formato Prometheus.

---

## **📌 Documentação da API (Swagger)**
- **Acesse Swagger UI:** [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)
- **Documentação JSON da API:** [`http://localhost:8080/v3/api-docs`](http://localhost:8080/v3/api-docs)

---

## **📌 Conteinerização (Docker)**
### **1️⃣ Dockerfile**
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/itub3-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
EXPOSE 8080
```

### **2️⃣ Construção e Execução**
```sh
# Construir o projeto
mvn clean package -DskipTests

# Criar a imagem Docker
docker build -t itub3-app .

# Executar o container
docker run -d -p 8080:8080 --name itub3-container itub3-app
```

### **3️⃣ Parar & Remover o Container**
```sh
docker stop itub3-container
docker rm itub3-container
docker rmi itub3-app
```

---

## **📌 Como Clonar e Executar Localmente**
### **1️⃣ Clonar o Repositório**
```sh
git clone https://github.com/YOUR_USERNAME/itub3.git
cd itub3
```

### **2️⃣ Construir o Projeto**
```sh
mvn clean install -DskipTests
```

### **3️⃣ Rodar a Aplicação**
```sh
mvn spring-boot:run
```

### **4️⃣ Rodar via Docker**
```sh
docker build -t itub3-app .
docker run -d -p 8080:8080 --name itub3-container itub3-app
```

---

## **📌 Testes**
### **Executar Testes Unitários**
```sh
mvn test
```

---

## **📌 Autor e Licença**
👤 **Saulo Capistrano**  
📧 Contato: `saulocapistrano@gmail.com`  
📄 Licença: MIT License

---

**🚀 Pronto para usar a API? Comece agora explorando o Swagger!**

[`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)
