package com.houssemmekhelbi.resource;

import com.houssemmekhelbi.model.Reference;
import com.houssemmekhelbi.model.payload.ReferenceRequest;
import com.houssemmekhelbi.service.Interface.ReferenceManagement;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Houssem Mekhelbi
 * @Project code-chronicles
 */
@QuarkusTest
public class ReferenceResourceTest {
    @InjectMocks
    private ReferenceResource referenceResource;
    @Mock
    private ReferenceManagement referenceManagement;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testRetrieveAllReferences() {
        // Mock the behavior of the ReferenceManagement dependency to return a list of references
        List<Reference> expectedReferences = Arrays.asList(
                new Reference(1L,
                        "Name 1",
                        "Email 1",
                        "Position 1",
                        "Picture 1",
                        "Review 1"),
                new Reference(2L,
                        "Name 2",
                        "Email 2",
                        "Position 2",
                        "Picture 2",
                        "Review 2")
        );
        when(referenceManagement.retrieveAllReferences()).thenReturn(Uni.createFrom().item(expectedReferences));

        // Call the retrieveAllReferences method and verify the returned list of references
        List<Reference> result = referenceManagement.retrieveAllReferences().await().indefinitely();
        assertEquals(expectedReferences, result);
    }
    @Test
    public void testRetrieveReferenceById() {
        // Prepare a mock reference
        Reference expectedReference = new Reference(1L,
                "Name",
                "Email",
                "Position",
                "Picture",
                "Review");
        long referenceId = 1L;

        // Mock the behavior of ReferenceManagement to return the mock reference for the specified ID
        when(referenceManagement.retrieveReferenceById(referenceId)).thenReturn(Uni.createFrom().item(expectedReference));

        // Call retrieveReferenceById with the reference ID
        Reference result = referenceResource.retrieveReferenceById(referenceId).await().indefinitely();

        // Verify that the returned reference matches the expected reference
        assertEquals(expectedReference, result);
    }
    @Test
    public void testSaveNewReference() {
        // Prepare a mock reference to be saved
        Reference savedReference = new Reference(1L,
                "Name",
                "Email",
                "Position",
                "Picture",
                "Review");
        ReferenceRequest newReferenceRequest = new ReferenceRequest("Name",
                "Email",
                "Position",
                "Picture",
                "Review");

        // Mock the behavior of ReferenceManagement to return the saved reference
        when(referenceResource.saveNewReference(newReferenceRequest)).thenReturn(Uni.createFrom().item(savedReference));

        // Call saveNewReference with the mock reference
        Reference result = referenceResource.saveNewReference(newReferenceRequest).await().indefinitely();

        // Verify that the returned reference matches the saved reference
        assertEquals(savedReference, result);
    }
    @Test
    public void testUpdateReference() {
        // Mock the behavior of the referenceManagement dependency to update an existing reference
        Reference reference = new Reference(1L,
                "Name",
                "Email",
                "Position",
                "Picture",
                "Review");
        when(referenceResource.updateReference(any(Reference.class))).thenReturn(Uni.createFrom().item(reference));

        // Call the update reference method with the updated reference and verify the returned reference
        Reference result = referenceResource.updateReference(reference).await().indefinitely();
        assertEquals(reference, result);
    }
    @Test
    public void testDeleteReference() {
        // Mock the behavior of the referenceManagement dependency to delete a reference
        long referenceId = 1L;
        when(referenceResource.deleteReference(referenceId)).thenReturn(Uni.createFrom().item(true));

        // Call the delete reference method with the reference ID and verify the returned boolean
        boolean result = referenceResource.deleteReference(referenceId).await().indefinitely();
        assertTrue(result);
    }
}
