package com.houssemmekhelbi.repository;

import com.houssemmekhelbi.model.Blog;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BlogRepository implements PanacheRepository<Blog> {
}
