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

    @Test
    void testDelete_notFound() {
        assertThrows(ResourceNotFoundException.class, () -> userService.delete(12345L));
    }

    @Test
    void testUpdate_success() {
        User u = new User(null, "Old", "old@test.com");
        User created = userService.create(u);

        User updated = new User(null, "New", "new@test.com");
        User result = userService.update(created.getId(), updated);

        assertEquals("New", result.getName());
        assertEquals("new@test.com", result.getEmail());
    }

    @Test
    void testUpdate_notFound() {
        User fake = new User(null, "Ghost", "ghost@test.com");
        assertThrows(ResourceNotFoundException.class, () -> userService.update(999L, fake));
    }
}
