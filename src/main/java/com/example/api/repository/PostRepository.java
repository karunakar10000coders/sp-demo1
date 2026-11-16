package com.example.api.repository;

import com.example.api.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final ConcurrentMap<Long, Post> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public PostRepository() {
        // Pre-populate with initial sample posts
        save(new Post(null, "Getting Started with Spring Boot 3", "Spring Boot makes building web microservices simple and efficient.", "Alice"));
        save(new Post(null, "Mastering RESTful APIs", "A guide on REST principles, HTTP methods, and status codes.", "Bob"));
        save(new Post(null, "Understanding Microservices Architecture", "How decoupling monolithic apps enables scalability and resilience.", "Charlie"));
    }

    public List<Post> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == null) {
            post.setId(idGenerator.incrementAndGet());
        }
        storage.put(post.getId(), post);
        return post;
    }

    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }

    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
