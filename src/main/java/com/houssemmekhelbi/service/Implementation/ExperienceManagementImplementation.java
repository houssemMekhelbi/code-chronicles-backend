package com.houssemmekhelbi.service.Implementation;

import com.houssemmekhelbi.Exception.NotFoundException;
import com.houssemmekhelbi.Exception.OperationException;
import com.houssemmekhelbi.Exception.RetrievalException;
import com.houssemmekhelbi.model.Experience;
import com.houssemmekhelbi.model.payload.ExperienceRequest;
import com.houssemmekhelbi.repository.ExperienceRepository;
import com.houssemmekhelbi.service.Interface.ExperienceManagement;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ExperienceManagementImplementation implements ExperienceManagement {
    @Inject
    ExperienceRepository experienceRepository;

    /**
     * Retrieves all experiences from the database.
     *
     * @return A Uni emitting a list of experience objects.
     * If no experiences are found, fails with NotFoundException.
     * If an error occurs during retrieval, fails with RetrievalException.
     */
    @Override
    public Uni<List<Experience>> retrieveAllExperiences() {
        return experienceRepository
                .listAll()
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("No experiences found"))
                .onFailure()
                .recoverWithUni(error -> Uni.createFrom()
                        .failure(new RetrievalException("Error retrieving experiences")));
    }

    /**
     * Retrieves an Experience by its ID from the database.
     *
     * @param id_experience The ID of the Experience to retrieve.
     * @return A Uni emitting an Experience object with the specified ID.
     * If the Experience is not found, fails with NotFoundException.
     * If an error occurs during retrieval, fails with RetrievalException.
     */
    @Override
    public Uni<Experience> retrieveExperienceById(long id_experience) {
        return experienceRepository
                .findById(id_experience)
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("Experience not found with ID: " + id_experience))
                .onFailure()
                .recoverWithUni(error -> Uni.createFrom()
                        .failure(new RetrievalException("Error retrieving Experience")));
    }

    /**
     * Saves a new experience to the database.
     *
     * @param experienceRequest The experienceRequest object containing information about the new experience.
     * @return A Uni emitting the newly saved experience object.
     * If an error occurs during saving, fails with OperationException.
     */
    @Override
    @Transactional
    public Uni<Experience> saveNewExperience(ExperienceRequest experienceRequest) {
        Experience experience = new Experience();
        experience.setTitle(experienceRequest.getTitle());
        experience.setStart_date(experienceRequest.getStart_date());
        experience.setEnd_date(experienceRequest.getEnd_date());
        experience.setCompany(experienceRequest.getCompany());
        experience.setWork_location(experienceRequest.getWork_location());
        experience.setProject_name(experienceRequest.getProject_name());
        experience.setProject_description(experienceRequest.getProject_description());
        return experienceRepository
                .persist(experience)
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new RetrievalException("Error retrieving Experience")));
    }

    /**
     * Updates an existing experience in the database.
     *
     * @param experience The Experience object containing updated information.
     * @return A Uni emitting the updated Experience object.
     * If the experience to be updated is not found, fails with NotFoundException.
     * If an error occurs during updating, fails with OperationException.
     */
    @Override
    @Transactional
    public Uni<Experience> updateExperience(Experience experience) {
        Uni<Experience> experienceOld = experienceRepository.findById(experience.getId());
        return experienceOld
                .onItem()
                .ifNotNull()
                .transform(entity -> {
                    entity.setTitle(experience.getTitle());
                    entity.setStart_date(experience.getStart_date());
                    entity.setEnd_date(experience.getEnd_date());
                    entity.setCompany(experience.getCompany());
                    entity.setWork_location(experience.getWork_location());
                    entity.setProject_name(experience.getProject_name());
                    entity.setProject_description(experience.getProject_description());
                    entity.setId(experience.getId());
                    return entity;
                })
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new OperationException("Error updating experience")));
    }

    /**
     * Deletes an experience from the database.
     *
     * @param experience_id The experience object's ID to be deleted.
     * @return A Uni emitting true if deletion is successful, false otherwise.
     * If an error occurs during deletion, fails with OperationException.
     */
    @Override
    @Transactional
    public Uni<Boolean> deleteExperience(long experience_id) {
        return experienceRepository
                .deleteById(experience_id)
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new OperationException("Error deletion experience")));
    }
}
