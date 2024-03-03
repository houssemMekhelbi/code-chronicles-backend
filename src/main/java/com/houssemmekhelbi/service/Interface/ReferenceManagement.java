package com.houssemmekhelbi.service.Interface;

import com.houssemmekhelbi.model.Reference;
import com.houssemmekhelbi.model.payload.ReferenceRequest;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ReferenceManagement {
    Uni<List<Reference>> retrieveAllReferences();
    Uni<Reference> retrieveReferenceById(long id_reference);
    Uni<Reference> saveNewReference(ReferenceRequest referenceRequest);
    Uni<Reference> updateReference(Reference reference);
    Uni<Boolean> deleteReference(long reference_id);
}
