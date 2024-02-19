package com.houssemmekhelbi.repository;

import com.houssemmekhelbi.model.mainModels.Service;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ServiceRepository implements PanacheRepository<Service> {

}