package com.houssemmekhelbi.resource;

import com.houssemmekhelbi.model.FileFormData;
import com.houssemmekhelbi.model.FileObject;
import com.houssemmekhelbi.service.Interface.PictureSpaceManagement;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestMulti;

import java.util.List;

@Path("/do-space")
public class PictureSpaceResource {
    @Inject
    PictureSpaceManagement pictureSpaceManagement;

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Uni<Response> upload(FileFormData fileFormData) {
        return pictureSpaceManagement.upload(fileFormData);
    }


    @GET
    @Path("download/{objectKey}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public RestMulti<Buffer> download(String objectKey) {
        return pictureSpaceManagement.download(objectKey);
    }

    @GET
    @Path("download/{folder}/{filename}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public RestMulti<Buffer> download(@PathParam("folder") String folderName,
                                          @PathParam("filename") String filename) {
        return pictureSpaceManagement.download(String
                .join("/",folderName,filename));
    }


    @GET
    public Uni<List<FileObject>> retrieveAll() {
        return pictureSpaceManagement.retrieveAll();
    }
}