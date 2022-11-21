# Policy Management Project
A Policy Management Service to create, update and get policies.

# Getting Started

### Local setup
One can run the command `./gradlew bootRun` toi run the application.

### Tech stack

* **Kotlin** as PL
* **Spring Boot** as framework
* **JPA** as ORM
* **Gradle** as packaging and dependency control
* **H2** as DB
* **Spring Boot Test, Mockito and JUnit5** for testing
* **Spring Docs** as documentation and openapi

### Spec
OpenApiV3 document is located [here](spec.yml)
Swagger ui can be seen [here](http://localhost:8080/swagger-ui)

### Notes
The **swagger.ui**'s request samples are faulty because of the date format. So spec is the right one.