package com.houssemmekhelbi.resource;

import com.houssemmekhelbi.model.FileFormData;
import com.houssemmekhelbi.model.FileObject;
import com.houssemmekhelbi.service.Interface.PictureSpaceManagement;
import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestMulti;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Houssem Mekhelbi
 * @Project code-chronicles
 */
@QuarkusTest
public class PictureSpaceResourceTest {
    @InjectMocks
    private PictureSpaceResource pictureSpaceResource;

    @Mock
    private final PictureSpaceManagement pictureSpaceManagement;

    public PictureSpaceResourceTest(PictureSpaceManagement pictureSpaceManagement) {
        this.pictureSpaceManagement = pictureSpaceManagement;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testUpload() {
        // Mock behavior of PictureSpaceManagement to return a successful response
        when(pictureSpaceManagement.upload(any())).thenReturn(Uni.createFrom().item(Response.ok().build()));

        // Call the upload method with mock data and verify the response
        Response response = pictureSpaceResource.upload(mockFileFormData).await().indefinitely();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDownloadWithObjectKey() {
        // Mock behavior of PictureSpaceManagement to return a RestMulti<Buffer>
        when(pictureSpaceManagement.download(anyString())).thenReturn(mockRestMultiBuffer);

        // Call the download method with a mock object key and verify the response
        String mockObjectKey = "mockObjectKey";
        RestMulti<Buffer> restMultiBuffer = pictureSpaceResource.download(mockObjectKey);
        assertNotNull(restMultiBuffer);
    }

    @Test
    public void testDownloadWithFolderAndFilename() {
        // Mock behavior of PictureSpaceManagement to return a RestMulti<Buffer>
        when(pictureSpaceManagement.download(anyString())).thenReturn(mockRestMultiBuffer);

        // Call the download method with mock folder and filename and verify the response
        String mockFilename = "mockFilename";
        String mockFolderName = "mockFolderName";
        RestMulti<Buffer> restMultiBuffer = pictureSpaceResource.download(mockFolderName, mockFilename);
        assertNotNull(restMultiBuffer);
    }

    @Test
    public void testRetrieveAll() {
        // Mock behavior of PictureSpaceManagement to return a list of FileObject
        when(pictureSpaceManagement.retrieveAll()).thenReturn(Uni.createFrom().item(mockFileObjectList));

        // Call the retrieveAll method and verify the returned list
        List<FileObject> fileObjects = pictureSpaceResource.retrieveAll().await().indefinitely();
        assertEquals(mockFileObjectList, fileObjects);
    }

    // Mock data for testing
    private final FileFormData mockFileFormData = new FileFormData();
    private final RestMulti<Buffer> mockRestMultiBuffer = RestMulti.fromMultiData(Multi.createFrom().items(Buffer.buffer("mockData"))).build();
    private final List<FileObject> mockFileObjectList = List.of(new FileObject());

}
