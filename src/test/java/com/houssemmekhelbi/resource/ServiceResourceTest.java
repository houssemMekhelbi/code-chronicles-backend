package com.houssemmekhelbi.resource;

import com.houssemmekhelbi.model.Service;
import com.houssemmekhelbi.model.payload.ServiceRequest;
import com.houssemmekhelbi.service.Interface.ServiceManagement;
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
public class ServiceResourceTest {
    @InjectMocks
    private ServiceResource serviceResource;
    @Mock
    private ServiceManagement serviceManagement;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllServices() {
        // Mock the behavior of the ServiceManagement dependency to return a list of services
        List<Service> expectedServices = Arrays.asList(
                new Service(1L,
                        "Name 1",
                        "Description 1",
                        "Logo 1"),
                new Service(2L,
                        "Name 2",
                        "Description 2",
                        "Logo 2")
        );
        when(serviceManagement.retrieveAllServices()).thenReturn(Uni.createFrom().item(expectedServices));

        // Call the retrieveAllServices method and verify the returned list of services
        List<Service> result = serviceResource.retrieveAllServices().await().indefinitely();
        assertEquals(expectedServices, result);
    }

    @Test
    public void testRetrieveServiceById() {
        // Prepare a mock service
        Service expectedService = new Service(1L,
                "Name",
                "Service",
                "Logo");
        long serviceId = 1L;

        // Mock the behavior of ServiceManagement to return the mock service for the specified ID
        when(serviceManagement.retrieveServiceById(serviceId)).thenReturn(Uni.createFrom().item(expectedService));

        // Call retrieveServiceById with the service ID
        Service result = serviceResource.retrieveServiceById(serviceId).await().indefinitely();

        // Verify that the returned service matches the expected service
        assertEquals(expectedService, result);
    }

    @Test
    public void testSaveNewService() {
        // Prepare a mock service to be saved
        Service savedService = new Service(1L,
                "New Title",
                "New Project",
                "New Description");
        ServiceRequest newServiceRequest =  new ServiceRequest("Name",
                "Service",
                "Logo");

        // Mock the behavior of ServiceManagement to return the saved service
        when(serviceManagement.saveNewService(newServiceRequest)).thenReturn(Uni.createFrom().item(savedService));

        // Call saveNewService with the mock service
        Service result = serviceResource.saveNewService(newServiceRequest).await().indefinitely();

        // Verify that the returned service matches the saved service
        assertEquals(savedService, result);
    }

    @Test
    public void testUpdateService() {
        // Mock the behavior of the serviceManagement dependency to update an existing service
        Service service =  new Service(1L,
                "Name",
                "Service",
                "Logo");
        when(serviceManagement.updateService(any(Service.class))).thenReturn(Uni.createFrom().item(service));

        // Call the updateservice method with the updated service and verify the returned service
        Service result = serviceResource.updateService(service).await().indefinitely();
        assertEquals(service, result);
    }

    @Test
    public void testDeleteService() {
        // Mock the behavior of the serviceManagement dependency to delete a service
        long serviceId = 1L;
        when(serviceManagement.deleteService(serviceId)).thenReturn(Uni.createFrom().item(true));

        // Call the deleteservice method with the service ID and verify the returned boolean
        boolean result = serviceManagement.deleteService(serviceId).await().indefinitely();
        assertTrue(result);
    }
}


