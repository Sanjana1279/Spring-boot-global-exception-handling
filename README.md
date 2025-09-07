# Spring Boot Global Exception Handling

This is a **Spring Boot project** demonstrating:

- REST API for `User` CRUD operations
- Global exception handling with `@ControllerAdvice`
- Custom exceptions (`ResourceNotFoundException`, `BadRequestException`)
- Validation with `@Valid` and `jakarta.validation`
- Unit tests with JUnit 5 and MockMvc

## Running the Project

The application will run on http://localhost:8080/users

**API Endpoints**

Method	Endpoint	Description
GET	/api/users	Get all users
GET	/api/users/{id}	Get user by ID
POST	/api/users	Create a new user
PUT	/api/users/{id}	Update user
DELETE	/api/users/{id}	Delete user

---
**Testing**
