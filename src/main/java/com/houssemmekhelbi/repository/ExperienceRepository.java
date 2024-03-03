package com.houssemmekhelbi.repository;

import com.houssemmekhelbi.model.Experience;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExperienceRepository implements PanacheRepository<Experience> {
}
