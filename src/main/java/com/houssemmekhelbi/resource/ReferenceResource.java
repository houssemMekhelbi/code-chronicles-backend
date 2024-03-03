package com.houssemmekhelbi.resource;

import com.houssemmekhelbi.model.Reference;
import com.houssemmekhelbi.model.payload.ReferenceRequest;
import com.houssemmekhelbi.service.Interface.ReferenceManagement;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@Path("reference")
@WithTransaction
public class ReferenceResource {
    @Inject
    ReferenceManagement referenceManagement;

    @GET
    @Produces("application/json")
    public Uni<List<Reference>> retrieveAllReferences() {
        return referenceManagement.retrieveAllReferences();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Uni<Reference> retrieveReferenceById(@PathParam("id") Long id) {
        return referenceManagement.retrieveReferenceById(id);
    }

    @POST
    @Produces("application/json")
    public Uni<Reference> saveNewReference(ReferenceRequest referenceRequest) {
        return referenceManagement.saveNewReference(referenceRequest);
    }

    @PUT
    @Produces("application/json")
    public Uni<Reference> updateReference(Reference reference) {
        return referenceManagement.updateReference(reference);
    }

    @DELETE
    @Produces("application/json")
    public Uni<Boolean> deleteReference(long reference_id) {
        return referenceManagement.deleteReference(reference_id);
    }
}
