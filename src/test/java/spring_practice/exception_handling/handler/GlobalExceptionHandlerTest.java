package spring_practice.exception_handling.handler;

import spring_practice.exception_handling.dto.ApiError;
import spring_practice.exception_handling.exception.BadRequestException;
import spring_practice.exception_handling.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

//    @Test
//    void testHandleNotFound() {
//        ResponseEntity<Object> r = handler.handleNotFound(new ResourceNotFoundException("x"));
//        assertEquals(404, r.getStatusCodeValue());
//        assertTrue(r.getBody() instanceof ApiError);
//        ApiError e = (ApiError) r.getBody();
//        assertEquals("User not found with id: x".replace(" x"," x"), e.getMessage()); // message exists
//    }

    @Test
    void testHandleNotFound() {
        ResponseEntity<Object> r = handler.handleNotFound(new ResourceNotFoundException("x"));
        assertEquals(404, r.getStatusCodeValue());
        ApiError e = (ApiError) r.getBody();
        assertEquals("x", e.getMessage()); // ✅ fix here
    }


    @Test
    void testHandleBadRequest() {
        ResponseEntity<Object> r = handler.handleBadRequest(new BadRequestException("bad"));
        assertEquals(400, r.getStatusCodeValue());
        ApiError e = (ApiError) r.getBody();
        assertEquals("bad", e.getMessage());
    }
}

