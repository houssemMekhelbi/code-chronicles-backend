package com.houssemmekhelbi.service.Implementation;

import com.houssemmekhelbi.Exception.NotFoundException;
import com.houssemmekhelbi.Exception.OperationException;
import com.houssemmekhelbi.Exception.RetrievalException;
import com.houssemmekhelbi.model.Reference;
import com.houssemmekhelbi.model.payload.ReferenceRequest;
import com.houssemmekhelbi.repository.ReferenceRepository;
import com.houssemmekhelbi.service.Interface.ReferenceManagement;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ReferenceManagementImplementation implements ReferenceManagement {
   @Inject
    ReferenceRepository referenceRepository;

    /**
     * Retrieves all references from the database.
     *
     * @return A Uni emitting a list of Reference objects.
     * If no references are found, fails with NotFoundException.
     * If an error occurs during retrieval, fails with RetrievalException.
     */
    @Override
    public Uni<List<Reference>> retrieveAllReferences() {
        return referenceRepository
                .listAll()
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("No references found"))
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new RetrievalException("Error retrieving references")));
    }
    /**
     * Retrieves a reference by its ID from the database.
     *
     * @param id_reference The ID of the reference to retrieve.
     * @return A Uni emitting a Reference object with the specified ID.
     * If the reference is not found, fails with NotFoundException.
     * If an error occurs during retrieval, fails with RetrievalException.
     */
    @Override
    public Uni<Reference> retrieveReferenceById(long id_reference) {
        return referenceRepository
                .findById(id_reference)
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("Reference not found with ID: " + id_reference))
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new RetrievalException("Error retrieving Reference")));
    }

    /**
     * Saves a new reference to the database.
     *
     * @param referenceRequest The ReferenceRequest object containing information about the new reference.
     * @return A Uni emitting the newly saved Reference object.
     * If an error occurs during saving, fails with OperationException.
     */
    @Override
    @Transactional
    public Uni<Reference> saveNewReference(ReferenceRequest referenceRequest) {
        Reference reference = new Reference();
        reference.setName(referenceRequest.getName());
        reference.setEmail(referenceRequest.getEmail());
        reference.setPosition(referenceRequest.getPosition());
        reference.setPicture(referenceRequest.getPicture());
        reference.setReview(referenceRequest.getReview());
        return referenceRepository
                .persist(reference)
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new RetrievalException("Error retrieving reference")));
    }

    /**
     * Updates an existing reference in the database.
     *
     * @param reference The Reference object containing updated information.
     * @return A Uni emitting the updated Reference object.
     * If the reference to be updated is not found, fails with NotFoundException.
     * If an error occurs during updating, fails with OperationException.
     */
    @Override
    @Transactional
    public Uni<Reference> updateReference(Reference reference) {
        Uni<Reference> referenceUni = referenceRepository.findById(reference.getId());
        return referenceUni
                .onItem()
                .ifNotNull()
                .transform(entity -> {
                    entity.setId(reference.getId());
                    entity.setName(reference.getName());
                    entity.setEmail(reference.getEmail());
                    entity.setPosition(reference.getPosition());
                    entity.setPicture(reference.getPicture());
                    entity.setReview(reference.getReview());
                    return entity;
                })
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new OperationException("Error updating reference")));
    }

    /**
     * Deletes a reference from the database.
     *
     * @param reference_id The reference object's ID to be deleted.
     * @return A Uni emitting true if deletion is successful, false otherwise.
     * If an error occurs during deletion, fails with OperationException.
     */
    @Override
    @Transactional
    public Uni<Boolean> deleteReference(long reference_id) {
        return referenceRepository
                .deleteById(reference_id)
                .onFailure()
                .recoverWithUni(error -> Uni
                        .createFrom()
                        .failure(new OperationException("Error deletion reference")));
    }
}
