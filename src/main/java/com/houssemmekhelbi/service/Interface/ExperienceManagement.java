package com.houssemmekhelbi.service.Interface;

import com.houssemmekhelbi.model.Experience;
import com.houssemmekhelbi.model.payload.ExperienceRequest;
import io.smallrye.mutiny.Uni;

import java.util.List;


public interface ExperienceManagement {
    Uni<List<Experience>> retrieveAllExperiences();
    Uni<Experience> retrieveExperienceById(long id_experience);
    Uni<Experience> saveNewExperience(ExperienceRequest experienceRequest);
    Uni<Experience> updateExperience(Experience experience);
    Uni<Boolean> deleteExperience(long experience_id);

}
