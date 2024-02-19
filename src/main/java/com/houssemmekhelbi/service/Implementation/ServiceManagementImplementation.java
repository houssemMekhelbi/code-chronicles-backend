package com.houssemmekhelbi.service.Implementation;

import com.houssemmekhelbi.Exception.ServiceNotFoundException;
import com.houssemmekhelbi.Exception.ServiceOperationException;
import com.houssemmekhelbi.Exception.ServiceRetrievalException;
import com.houssemmekhelbi.model.mainModels.Service;
import com.houssemmekhelbi.model.payload.ServiceRequest;
import com.houssemmekhelbi.repository.ServiceRepository;
import com.houssemmekhelbi.service.ServiceManagement;
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
     * @return A Uni emitting a list of Service objects.
     *         If no services are found, fails with ServiceNotFoundException.
     *         If an error occurs during retrieval, fails with ServiceRetrievalException.
     */
    @Override
    public Uni<List<Service>> retrieveAllServices() {
        return serviceRepository.listAll()
                .onItem().ifNull().failWith(() -> new ServiceNotFoundException("No services found"))
                .onFailure().recoverWithUni(error -> Uni.createFrom().failure(new ServiceRetrievalException("Error retrieving services")));
    }

    /**
     * Retrieves a service by its ID from the database.
     * @param id_service The ID of the service to retrieve.
     * @return A Uni emitting a Service object with the specified ID.
     *         If the service is not found, fails with ServiceNotFoundException.
     *         If an error occurs during retrieval, fails with ServiceRetrievalException.
     */
    @Override
    public Uni<Service> retrieveServiceById(long id_service) {
        return serviceRepository.findById(id_service)
                .onItem().ifNull().failWith(() -> new ServiceNotFoundException("Service not found with ID: " + id_service))
                .onFailure().recoverWithUni(error -> Uni.createFrom().failure(new ServiceRetrievalException("Error retrieving service")));
    }

    /**
     * Saves a new service to the database.
     * @param serviceRequest The ServiceRequest object containing information about the new service.
     * @return A Uni emitting the newly saved Service object.
     *         If an error occurs during saving, fails with ServiceOperationException.
     */
    @Override
    @Transactional
    public Uni<Service> saveNewService(ServiceRequest serviceRequest) {
        Service service = new Service();
        service.setName(serviceRequest.getName());
        service.setDescription(serviceRequest.getDescription());
        service.setLogoUrl(serviceRequest.getLogoUrl());
        return serviceRepository.persist(service)
                .onFailure().recoverWithUni(error -> {
                    System.err.println("Error saving new service: " + error.getMessage());
                    throw new ServiceOperationException("Error saving new service", error);
                });
    }

    /**
     * Updates an existing service in the database.
     * @param service The Service object containing updated information.
     * @return A Uni emitting the updated Service object.
     *         If the service to be updated is not found, fails with ServiceNotFoundException.
     *         If an error occurs during updating, fails with ServiceOperationException.
     */
    @Override
    @Transactional
    public Uni<Service> updateService(Service service) {
        Uni<Service> serviceOld = serviceRepository.findById(service.getId());
        return serviceOld.onItem().ifNotNull()
                .transform(entity -> {
                    entity.setId(service.getId());
                    entity.setName(service.getName());
                    entity.setDescription(service.getDescription());
                    entity.setLogoUrl(service.getLogoUrl());
                    return entity;
                }).onFailure().recoverWithUni(error -> {
                    System.err.println("Error updating service: " + error.getMessage());
                    throw new ServiceOperationException("Error updating service", error);
                });
    }

    /**
     * Deletes a service from the database.
     * @param service The Service object to be deleted.
     * @return A Uni emitting true if deletion is successful, false otherwise.
     *         If an error occurs during deletion, fails with ServiceOperationException.
     */
    @Override
    @Transactional
    public Uni<Boolean> deleteService(Service service) {
        return serviceRepository.deleteById(service.getId());
    }
}
