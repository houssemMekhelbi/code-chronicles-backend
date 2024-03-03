package com.houssemmekhelbi.service.Interface;

import com.houssemmekhelbi.model.Service;
import com.houssemmekhelbi.model.payload.ServiceRequest;
import io.smallrye.mutiny.Uni;

import java.util.List;


public interface ServiceManagement {
    Uni<List<Service>> retrieveAllServices();
    Uni<Service> retrieveServiceById(long id_service);
    Uni<Service> saveNewService(ServiceRequest serviceRequest);
    Uni<Service> updateService(Service service);
    Uni<Boolean> deleteService(long service_id);

}
