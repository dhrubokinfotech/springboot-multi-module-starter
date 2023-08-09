# Spring Boot Multi-Module Starter Boilerplate

### Description
Spring Boot Starter Boilerplate For Multi-Modular Project

### Technology
* Language: Java 17
* Build Tool: Maven
* Spring Boot: 3.1.0
* Database: PostgreSQL

### Feature
- [x] REST API - User [CRUD]
- [x] REST API - Role [CRUD]
- [x] REST API - Notification [CRUD]
- [x] REST API - Language [CRUD]
- [x] REST API - Translation [CRUD]
- [x] Language Support From Database [Translation]
- [x] File Handling
- [x] Spring Security [Config - JWT Authentication, Form Login]
- [x] Role/Permission Based Authorization
- [x] Add Validation & Constraints for REST API & DB Entity
- [x] Global Exception Handling [Config]
- [x] Logging [Config]
- [x] API Doc: Swagger [Config]
- [x] Apply Dynamic Query via JPA Specification

### Clone Repository
Clone this repository from https://github.com/dhrubokinfotech/springboot-multi-module-starter.git or use the below command for the GitHub CLI:

```
gh repo clone dhrubokinfotech/springboot-multi-module-starter
```

### Build & Run
There are several ways to build and run a Spring Boot application. After cloning this repository, you can build it using the below command:

```
mvn clean install
```

Before giving this command, make sure that Maven is already installed on your PC. This command will clear the target folder and previously generated JAR files, build an artifact, and then create a new JAR file,  <span style = "color: cyan">starter-core-0.0.1-SNAPSHOT.jar</span> inside the target directory. Now, you can run this JAR file using the below command:

```
java -jar starter-core\target\starter-core-0.0.1-SNAPSHOT.jar 
```

Also, you can directly run this application without an explicit build using the below command:

```
mvn spring-boot:run
```

Or, you can run this application via the <span style = "color: cyan">Run</span> button of your preferred IDE.

You can get the REST APIs with documentation here: http://localhost:8081/swagger-ui/index.html.