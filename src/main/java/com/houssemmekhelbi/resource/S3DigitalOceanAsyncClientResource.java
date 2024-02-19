package com.houssemmekhelbi.resource;

import com.houssemmekhelbi.configuration.CommonResource;
import com.houssemmekhelbi.model.secondaryModels.FileObject;
import com.houssemmekhelbi.model.secondaryModels.FormData;
import com.houssemmekhelbi.utils.PictureManagementFunction;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import mutiny.zero.flow.adapters.AdaptersToFlow;
import org.jboss.resteasy.reactive.RestMulti;
import org.reactivestreams.Publisher;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

@Path("/do-space")
public class S3DigitalOceanAsyncClientResource extends CommonResource {
    @Inject
    S3AsyncClient s3;

    /**
     * Uploads a file to S3.
     * @param formData The FormData object containing file data.
     * @return A Uni emitting a response indicating success or failure.
     */
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Uni<Response> uploadFile(FormData formData) {

        if (formData.filename == null || formData.filename.isEmpty()) {
            return Uni.createFrom().item(Response.status(Status.BAD_REQUEST).entity("File name not found").build());
        }

        if (formData.mimetype == null || formData.mimetype.isEmpty()) {
            return Uni.createFrom().item(Response.status(Status.BAD_REQUEST).build());
        }

        return Uni.createFrom()
                .completionStage(() -> {
                    return s3.putObject(buildPutRequest(formData), AsyncRequestBody.fromFile(formData.data));
                })
                .onItem().ignore().andSwitchTo(Uni.createFrom().item(Response.created(null).build()))
                .onFailure().recoverWithItem(th -> Response.serverError().build());
    }

    /**
     * Downloads a file from S3.
     * @param objectKey The key of the object to download.
     * @return A RestMulti emitting the file content.
     */
    @GET
    @Path("download/{objectKey}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public RestMulti<Buffer> downloadFile(String objectKey) {

        return RestMulti.fromUniResponse(Uni.createFrom()
                        .completionStage(() -> s3.getObject(buildGetRequest(objectKey),
                                AsyncResponseTransformer.toPublisher())),
                response -> Multi.createFrom().safePublisher(AdaptersToFlow.publisher((Publisher<ByteBuffer>) response))
                        .map(PictureManagementFunction::toBuffer),
                response -> Map.of("Content-Disposition", List.of("attachment;filename=" + objectKey), "Content-Type",
                        List.of(response.response().contentType())));
    }

    /**
     * Lists files in the S3 bucket.
     * @return A Uni emitting a list of FileObject representing the files in the bucket.
     */
    @GET
    public Uni<List<FileObject>> listFiles() {
        ListObjectsRequest listRequest = ListObjectsRequest.builder()
                .bucket(bucketName)
                .build();

        return Uni.createFrom().completionStage(() -> s3.listObjects(listRequest))
                .onItem().transform(PictureManagementFunction::toFileItems);
    }
}