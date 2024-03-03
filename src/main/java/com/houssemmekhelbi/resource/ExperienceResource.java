package com.houssemmekhelbi.resource;

import com.houssemmekhelbi.model.Experience;
import com.houssemmekhelbi.model.payload.ExperienceRequest;
import com.houssemmekhelbi.service.Interface.ExperienceManagement;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.List;

@Path("/experience")
@WithTransaction
public class ExperienceResource {
    @Inject
    ExperienceManagement experienceManagement;

    @GET
    @Produces("application/json")
    public Uni<List<Experience>> retrieveAllExperiences() {
        return experienceManagement.retrieveAllExperiences();
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Uni<Experience> retrieveExperienceById(@PathParam("id") Long id) {
        return experienceManagement.retrieveExperienceById(id);
    }

    @POST
    @Produces("application/json")
    public Uni<Experience> saveNewExperience(ExperienceRequest experienceRequest) {
        return experienceManagement.saveNewExperience(experienceRequest);
    }

    @PUT
    @Produces("application/json")
    public Uni<Experience> updateExperience(Experience experience) {
        return experienceManagement.updateExperience(experience);
    }

    @DELETE
    @Produces("application/json")
    public Uni<Boolean> deleteExperience(long experience_id) {
        return experienceManagement.deleteExperience(experience_id);
    }
}
