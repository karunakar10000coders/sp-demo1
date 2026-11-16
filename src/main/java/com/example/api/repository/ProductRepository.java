package com.example.api.repository;

import com.example.api.model.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
    private final ConcurrentMap<Long, Product> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public ProductRepository() {
        // Pre-populate with initial sample products
        save(new Product(null, "Wireless Noise-Canceling Headphones", "High fidelity over-ear headphones with 30-hour battery life", new BigDecimal("199.99"), "Electronics", 45));
        save(new Product(null, "Ergonomic Mechanical Keyboard", "RGB backlit keyboard with custom hot-swappable switches", new BigDecimal("129.50"), "Accessories", 80));
        save(new Product(null, "Ultra-Wide 4K Gaming Monitor", "34-inch curved display with 144Hz refresh rate", new BigDecimal("499.00"), "Electronics", 20));
        save(new Product(null, "Standing Desk Mat", "Anti-fatigue comfort mat for standing workstations", new BigDecimal("39.95"), "Furniture", 110));
    }

    public List<Product> findAll() {
        return new ArrayList<>(storage.values());
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(idGenerator.incrementAndGet());
        }
        storage.put(product.getId(), product);
        return product;
    }

    public boolean deleteById(Long id) {
        return storage.remove(id) != null;
    }

    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
