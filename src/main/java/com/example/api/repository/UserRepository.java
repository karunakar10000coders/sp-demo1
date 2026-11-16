package com.example.api.repository;

import com.example.api.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {
    private final ConcurrentMap<Long, User> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public UserRepository() {
        // Pre-populate with initial sample users
        save(new User(null, "Alice Smith", "alice.smith@example.com", "ADMIN", true));
        save(new User(null, "Bob Johnson", "bob.johnson@example.com", "USER", true));
        save(new User(null, "Charlie Brown", "charlie.brown@example.com", "USER", false));
    }

    public List<User> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Optional<User> findByEmail(String email) {
        return storage.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.incrementAndGet());
        }
        storage.put(user.getId(), user);
        return user;
    }

    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }

    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
