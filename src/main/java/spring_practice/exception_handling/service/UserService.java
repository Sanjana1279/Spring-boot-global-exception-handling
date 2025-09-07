package spring_practice.exception_handling.service;

import spring_practice.exception_handling.exception.ResourceNotFoundException;
import spring_practice.exception_handling.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {
    private final Map<Long, User> store = Collections.synchronizedMap(new HashMap<>());
    private final AtomicLong idGen = new AtomicLong(1);

    public UserService() {
        // seed data
        User u1 = new User(idGen.getAndIncrement(), "Alice", "alice@example.com");
        User u2 = new User(idGen.getAndIncrement(), "Bob", "bob@example.com");
        store.put(u1.getId(), u1);
        store.put(u2.getId(), u2);
    }

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public User findById(Long id) {
        User u = store.get(id);
        if (u == null) throw new ResourceNotFoundException("User not found with id: " + id);
        return u;
    }

    public User create(User user) {
        long id = idGen.getAndIncrement();
        user.setId(id);
        store.put(id, user);
        return user;
    }

    public User update(Long id, User user) {
        User existing = store.get(id);
        if (existing == null) throw new ResourceNotFoundException("User not found with id: " + id);
        existing.setName(user.getName());
        existing.setEmail(user.getEmail());
        store.put(id, existing);
        return existing;
    }

    public void delete(Long id) {
        User removed = store.remove(id);
        if (removed == null) throw new ResourceNotFoundException("User not found with id: " + id);
    }
}
