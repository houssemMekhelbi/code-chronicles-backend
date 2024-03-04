package com.houssemmekhelbi.service.Implementation;

import com.houssemmekhelbi.Exception.NotFoundException;
import com.houssemmekhelbi.Exception.OperationException;
import com.houssemmekhelbi.Exception.RetrievalException;
import com.houssemmekhelbi.model.Service;
import com.houssemmekhelbi.model.payload.ServiceRequest;
import com.houssemmekhelbi.repository.ServiceRepository;
import com.houssemmekhelbi.service.Interface.ServiceManagement;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ServiceManagementImplementation implements ServiceManagement {

    @Inject
    ServiceRepository serviceRepository;

    /**
     * Retrieves all services from the database.
     *
     * @return A Uni emitting a list of Service objects.
     * If no services are found, fails with NotFoundException.
     * If an error occurs during retrieval, fails with RetrievalException.
     */
    @Override
    public Uni<List<Service>> retrieveAllServices() {
        return serviceRepository
                .listAll()
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("No services found"))
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new RetrievalException("Error retrieving services")));
    }

    /**
     * Retrieves a service by its ID from the database.
     *
     * @param id_service The ID of the service to retrieve.
     * @return A Uni emitting a Service object with the specified ID.
     * If the service is not found, fails with NotFoundException.
     * If an error occurs during retrieval, fails with RetrievalException.
     */
    @Override
    public Uni<Service> retrieveServiceById(long id_service) {
        return serviceRepository
                .findById(id_service)
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("Service not found with ID: " + id_service))
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new RetrievalException("Error retrieving service")));
    }

    /**
     * Saves a new service to the database.
     *
     * @param serviceRequest The ServiceRequest object containing information about the new service.
     * @return A Uni emitting the newly saved Service object.
     * If an error occurs during saving, fails with OperationException.
     */
    @Override
    @Transactional
    public Uni<Service> saveNewService(ServiceRequest serviceRequest) {
        Service service = new Service();
        service.setName(serviceRequest.getName());
        service.setDescription(serviceRequest.getDescription());
        service.setLogoUrl(serviceRequest.getLogoUrl());
        return serviceRepository
                .persist(service)
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new RetrievalException("Error retrieving service")));
    }


    /**
     * Updates an existing service in the database.
     *
     * @param service The Service object containing updated information.
     * @return A Uni emitting the updated Service object.
     * If the service to be updated is not found, fails with NotFoundException.
     * If an error occurs during updating, fails with OperationException.
     */
    @Override
    @Transactional
    public Uni<Service> updateService(Service service) {
        Uni<Service> serviceOld = serviceRepository.findById(service.getId());
        return serviceOld
                .onItem()
                .ifNotNull()
                .transform(entity -> {
                    entity.setName(service.getName());
                    entity.setDescription(service.getDescription());
                    entity.setLogoUrl(service.getLogoUrl());
                    entity.setId(service.getId());
                    return entity;
                })
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new OperationException("Error updating service")));
    }

    /**
     * Deletes a service from the database.
     *
     * @param service_id The Service object's ID to be deleted.
     * @return A Uni emitting true if deletion is successful, false otherwise.
     * If an error occurs during deletion, fails with OperationException.
     */
    @Override
    @Transactional
    public Uni<Boolean> deleteService(long service_id) {
        return serviceRepository
                .deleteById(service_id)
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new OperationException("Error deletion service")));
    }
}
