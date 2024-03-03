package com.houssemmekhelbi.service.Implementation;

import com.houssemmekhelbi.configuration.CommonResource;
import com.houssemmekhelbi.model.FileFormData;
import com.houssemmekhelbi.model.FileObject;
import com.houssemmekhelbi.service.Interface.PictureSpaceManagement;
import com.houssemmekhelbi.utils.PictureManagementFunction;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import mutiny.zero.flow.adapters.AdaptersToFlow;
import org.jboss.resteasy.reactive.RestMulti;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;

import java.util.List;
import java.util.Map;

@ApplicationScoped
public class PictureSpaceManagementImplementation extends CommonResource implements PictureSpaceManagement  {
    @Inject
    S3AsyncClient s3;

    /**
     * Lists files in the DigitalOcean Space Storage's bucket.
     *
     * @return A Uni emitting a list of FileObject representing the files in the bucket.
     */
    @Override
    public Uni<List<FileObject>> retrieveAll() {
        ListObjectsRequest listRequest = ListObjectsRequest
                .builder()
                .bucket(bucketName)
                .build();

        return Uni
                .createFrom()
                .completionStage(() -> s3
                        .listObjects(listRequest))
                .onItem()
                .transform(PictureManagementFunction::toFileItems);
    }

    /**
     * Uploads a file to DigitalOcean Space Storage.
     *
     * @param fileFormData The FormData object containing file data.
     * @return A Uni emitting a response indicating success or failure.
     */
    @Override
    public Uni<Response> upload(FileFormData fileFormData) {
            if (fileFormData.filename == null || fileFormData.filename.isEmpty()) {
                return Uni
                        .createFrom()
                        .item(Response
                                .status(Response.Status.BAD_REQUEST)
                                .entity("File name not found")
                                .build());
            }

            if (fileFormData.mimetype == null || fileFormData.mimetype.isEmpty()) {
                return Uni
                        .createFrom()
                        .item(Response
                                .status(Response.Status.BAD_REQUEST)
                                .build());
            }

            return Uni.createFrom()
                    .completionStage(() -> s3
                            .putObject(buildPutRequest(fileFormData),
                                    AsyncRequestBody.fromFile(fileFormData.data)))
                    .onItem()
                    .ignore()
                    .andSwitchTo(Uni
                            .createFrom()
                            .item(Response
                                    .ok(fileFormData.filename)
                                    .status(Response.Status.CREATED)
                                    .build()))
                    .onFailure()
                    .recoverWithItem(th -> Response
                            .serverError()
                            .build());
        }

    /**
     * Downloads a file without accessing folder from DigitalOcean Storage Management.
     *
     * @param objectKey The key of the file to download.
     * @return A RestMulti emitting the file content.
     */
    @Override
    public RestMulti<Buffer> download(String objectKey) {
        return RestMulti
                .fromUniResponse(Uni
                                .createFrom()
                                .completionStage(() -> s3
                                        .getObject(buildGetRequest(objectKey),
                                                AsyncResponseTransformer
                                                        .toPublisher())),
                        response -> Multi
                                .createFrom()
                                .safePublisher(AdaptersToFlow
                                        .publisher(response))
                                .map(PictureManagementFunction::toBuffer),
                        response -> Map
                                .of("Content-Disposition", List
                                                .of("attachment;filename=" + objectKey), "Content-Type",
                                        List
                                                .of(response
                                                        .response()
                                                        .contentType())));

    }
}
