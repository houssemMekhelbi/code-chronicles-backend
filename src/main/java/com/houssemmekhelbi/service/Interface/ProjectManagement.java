package com.houssemmekhelbi.service.Interface;

import com.houssemmekhelbi.model.Project;
import com.houssemmekhelbi.model.payload.ProjectRequest;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ProjectManagement {
    Uni<List<Project>> retrieveAllProjects();
    Uni<Project> retrieveProjectById(long id_project);
    Uni<Project> saveNewProject(ProjectRequest projectRequest);
    Uni<Project> updateProject(Project project);
    Uni<Boolean> deleteProject(long project_id);
}
