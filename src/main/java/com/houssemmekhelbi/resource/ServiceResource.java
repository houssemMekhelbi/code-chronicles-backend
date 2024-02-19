package com.houssemmekhelbi.resource;

import com.houssemmekhelbi.model.mainModels.Service;
import com.houssemmekhelbi.model.payload.ServiceRequest;
import com.houssemmekhelbi.service.ServiceManagement;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@Path("/services")
@WithTransaction
public class ServiceResource {
    @Inject
    ServiceManagement serviceManagement;

    @GET
    @Produces("application/json")
    public Uni<List<Service>> retrieveAllServices(){
        return serviceManagement.retrieveAllServices();
    }
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Uni<Service> retrieveServiceById(@PathParam("id") Long id){
        return serviceManagement.retrieveServiceById(id);
    }

    @POST
    @Produces("application/json")
    public Uni<Service> saveNewService(ServiceRequest serviceRequest){
      return serviceManagement.saveNewService(serviceRequest);
    }
    @PUT
    @Produces("application/json")
    public Uni<Service> updateService(Service service){
        return serviceManagement.updateService(service);
    }

    @DELETE
    @Produces("application/json")
    public Uni<Boolean> deleteService(Service service){
        return serviceManagement.deleteService(service);
    }
}
