package spring_practice.exception_handling.service;

import spring_practice.exception_handling.exception.ResourceNotFoundException;
import spring_practice.exception_handling.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testFindAll_notEmpty() {
        var list = userService.findAll();
        assertTrue(list.size() >= 2);
    }

    @Test
    void testFindById_found() {
        User u = userService.findAll().get(0);
        User fetched = userService.findById(u.getId());
        assertEquals(u.getId(), fetched.getId());
    }

    @Test
    void testFindById_notFound() {
        assertThrows(ResourceNotFoundException.class, () -> userService.findById(999L));
    }

    @Test
    void testCreateAndDelete() {
        User u = new User(null, "Test", "t@test.com");
        User created = userService.create(u);
        assertNotNull(created.getId());
        userService.delete(created.getId());
        assertThrows(ResourceNotFoundException.class, () -> userService.findById(created.getId()));
    }
}

