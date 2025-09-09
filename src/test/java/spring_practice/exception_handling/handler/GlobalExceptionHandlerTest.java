package spring_practice.exception_handling.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.WebRequest;
import spring_practice.exception_handling.dto.ApiError;
import spring_practice.exception_handling.exception.BadRequestException;
import spring_practice.exception_handling.exception.ResourceNotFoundException;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {   // ✅ no public

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleNotFound() {
        ResponseEntity<Object> r = handler.handleNotFound(new ResourceNotFoundException("x"));
        assertEquals(404, r.getStatusCode().value());
        ApiError e = (ApiError) r.getBody();
        assertEquals("x", e.getMessage());
    }

    @Test
    void testHandleBadRequest() {
        ResponseEntity<Object> r = handler.handleBadRequest(new BadRequestException("bad"));
        assertEquals(400, r.getStatusCode().value());
        ApiError e = (ApiError) r.getBody();
        assertEquals("bad", e.getMessage());
    }

    @Test
    void testHandleConstraintViolation() {
        ConstraintViolation<?> mockViolation = mock(ConstraintViolation.class);
        when(mockViolation.getMessage()).thenReturn("must not be null");

        ConstraintViolationException ex =
                new ConstraintViolationException(Set.of(mockViolation));

        ResponseEntity<Object> r = handler.handleConstraintViolation(ex);
        assertEquals(400, r.getStatusCode().value());
        ApiError e = (ApiError) r.getBody();
        assertEquals("Validation failed", e.getMessage());
        assertTrue(e.getDetails().contains("must not be null"));
    }

    @Test
    void testHandleHttpMessageNotReadable() {
        HttpMessageNotReadableException ex =
                new HttpMessageNotReadableException("Malformed JSON", (Throwable) null);

        HttpHeaders headers = new HttpHeaders();
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> r =
                handler.handleHttpMessageNotReadable(ex, headers, HttpStatusCode.valueOf(400), request);

        assertEquals(400, r.getStatusCode().value());
        ApiError e = (ApiError) r.getBody();
        assertEquals("Malformed JSON request", e.getMessage());
    }

    @Test
    void testHandleAll() {
        Exception ex = new Exception("boom");
        ResponseEntity<Object> r = handler.handleAll(ex);
        assertEquals(500, r.getStatusCode().value());
        ApiError e = (ApiError) r.getBody();
        assertEquals("An unexpected error occurred", e.getMessage());
        assertTrue(e.getDetails().contains("boom"));
    }
}
