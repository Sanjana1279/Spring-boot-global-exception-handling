# Spring Boot Global Exception Handling

<p align="center">
  <a href="https://sanjana1279.github.io/Spring-boot-global-exception-handling/jacoco-report/index.html">
    <img src="https://img.shields.io/badge/Coverage-73%25-yellowgreen" alt="Coverage">
  </a>
</p>

##  Table of Contents
- [Overview](#project-overview)
- [Code Coverage](#code-coverage-jacoco)
- [Technologies Used](#technologies-used)
- [Running the Project](#running-the-project)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Sample Requests & Responses](#sample-requests--responses)


---

##  Code Coverage (JaCoCo)

This project uses **JaCoCo** to measure test coverage.

- **Coverage Report**: [View full HTML report](https://sanjana1279.github.io/Spring-boot-global-exception-handling/jacoco-report/index.html)

---

##  Project Overview

This project demonstrates how to implement **global exception handling** in a Spring Boot REST API application.  
It provides a robust way to handle different types of exceptions and return meaningful error responses to clients in a consistent format.

- REST APIs for CRUD operations on User entities.  
- Custom exceptions (`ResourceNotFoundException`, `BadRequestException`).  
- Global exception handler (`@ControllerAdvice`) to handle exceptions across the application.  
- Validation using Jakarta Validation API (`@NotBlank`, `@Email`).  
- Unit and integration tests for services and controllers.  

---

##  Technologies Used
- Java 17  
- Spring Boot 3.1.x  
- Maven  
- JUnit 5  
- Mockito  
- Jakarta Validation API  
- Git & GitHub  

---

##  Running the Project
Start the application and it will be available at:  
 `http://localhost:8080/users`

---

## API Endpoints

| Method | Endpoint            | Description            |
|--------|---------------------|------------------------|
| GET    | `/api/users`        | Get all users          |
| GET    | `/api/users/{id}`   | Get a user by ID       |
| POST   | `/api/users`        | Create a new user      |
| PUT    | `/api/users/{id}`   | Update an existing user|
| DELETE | `/api/users/{id}`   | Delete a user by ID    |

---

## Testing

Run unit & integration tests with:
```bash
mvn test

```

## Sample Requests & Responses

**Sample Request**

```http
POST /api/users
Content-Type: application/json
```

```json
{
  "name": "Alice",
  "email": "alice@example.com"
}

```


**Sample Error Response**

```http
GET /api/users/99
Content-Type: application/json
```

```json
{
  "timestamp": "2025-09-07T19:14:09.631",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with id: 99",
  "details": null
}
```







