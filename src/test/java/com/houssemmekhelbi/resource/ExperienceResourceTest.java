package com.houssemmekhelbi.resource;

import com.houssemmekhelbi.model.Experience;
import com.houssemmekhelbi.model.payload.ExperienceRequest;
import com.houssemmekhelbi.service.Interface.ExperienceManagement;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Houssem Mekhelbi
 * @Project code-chronicles
 */
@QuarkusTest
public class ExperienceResourceTest {
    @InjectMocks
    private ExperienceResource experienceResource;
    @Mock
    private ExperienceManagement experienceManagement;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testRetrieveAllExperiences() {
        // Mock the behavior of the ExperienceManagement dependency to return a list of experiences
        List<Experience> expectedExperiences = Arrays.asList(
                new Experience(1L,
                        "Title 1",
                        new Date(TimeUnit.SECONDS.toMillis(1709453286)),
                        new Date(TimeUnit.SECONDS.toMillis(1709453286)),
                        "Company 1",
                        "Location 1",
                        "Project 1",
                        "Project Description 1"),
                new Experience(2L,
                        "Title 2",
                        new Date(TimeUnit.SECONDS.toMillis(1709453286)),
                        new Date(TimeUnit.SECONDS.toMillis(1709453286)),
                        "Company 2",
                        "Location 2",
                        "Project 2",
                        "Project Description 2")
        );
        when(experienceManagement.retrieveAllExperiences()).thenReturn(Uni.createFrom().item(expectedExperiences));

        // Call the retrieveAllExperiences method and verify the returned list of experiences
        List<Experience> result = experienceResource.retrieveAllExperiences().await().indefinitely();
        assertEquals(expectedExperiences, result);
    }
    @Test
    public void testRetrieveExperienceById() {
        // Prepare a mock experience
        Experience expectedExperience = new Experience(1L,
                "Title",
                new Date(TimeUnit.SECONDS.toMillis(1709453286)),
                new Date(TimeUnit.SECONDS.toMillis(1709453286)),
                "Company",
                "Location",
                "Project",
                "Description");
        long experienceId = 1L;

        // Mock the behavior of ExperienceManagement to return the mock experience for the specified ID
        when(experienceManagement.retrieveExperienceById(experienceId)).thenReturn(Uni.createFrom().item(expectedExperience));

        // Call retrieveExperienceById with the experience ID
        Experience result = experienceResource.retrieveExperienceById(experienceId).await().indefinitely();

        // Verify that the returned experience matches the expected experience
        assertEquals(expectedExperience, result);
    }
    @Test
    public void testSaveNewExperience() {
        // Prepare a mock experience to be saved
        Experience savedExperience = new Experience(1L,
                "New Title",
                new Date(TimeUnit.SECONDS.toMillis(1709453286)),
                new Date(TimeUnit.SECONDS.toMillis(1709453286)),
                "New Company",
                "New Location",
                "New Project",
                "New Description");
        ExperienceRequest newExperienceRequest = new ExperienceRequest("New Title",
                new Date(TimeUnit.SECONDS.toMillis(1709453286)),
                new Date(TimeUnit.SECONDS.toMillis(1709453286)),
                "New Company",
                "New Location",
                "New Project",
                "New Description");

        // Mock the behavior of ExperienceManagement to return the saved experience
        when(experienceManagement.saveNewExperience(newExperienceRequest)).thenReturn(Uni.createFrom().item(savedExperience));

        // Call saveNewExperience with the mock experience
        Experience result = experienceResource.saveNewExperience(newExperienceRequest).await().indefinitely();

        // Verify that the returned experience matches the saved experience
        assertEquals(savedExperience, result);
    }
    @Test
    public void testUpdateExperience() {
        // Mock the behavior of the experienceManagement dependency to update an existing experience
        Experience experience = new Experience(1L,
                "New Title",
                new Date(TimeUnit.SECONDS.toMillis(1709453286)),
                new Date(TimeUnit.SECONDS.toMillis(1709453286)),
                "New Company",
                "New Location",
                "New Project",
                "New Description");
        when(experienceManagement.updateExperience(any(Experience.class))).thenReturn(Uni.createFrom().item(experience));

        // Call the updateexperience method with the updated experience and verify the returned experience
        Experience result = experienceResource.updateExperience(experience).await().indefinitely();
        assertEquals(experience, result);
    }
    @Test
    public void testDeleteExperience() {
        // Mock the behavior of the experienceManagement dependency to delete a experience
        long experienceId = 1L;
        when(experienceManagement.deleteExperience(experienceId)).thenReturn(Uni.createFrom().item(true));

        // Call the deleteexperience method with the experience ID and verify the returned boolean
        boolean result = experienceManagement.deleteExperience(experienceId).await().indefinitely();
        assertTrue(result);
    }
}
