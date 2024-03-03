package com.houssemmekhelbi.repository;

import com.houssemmekhelbi.model.Reference;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReferenceRepository implements PanacheRepository<Reference> {
}
