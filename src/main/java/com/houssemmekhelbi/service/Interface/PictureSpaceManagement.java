package com.houssemmekhelbi.service.Interface;

import com.houssemmekhelbi.model.FileFormData;
import com.houssemmekhelbi.model.FileObject;
import io.smallrye.mutiny.Uni;
import io.vertx.core.buffer.Buffer;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestMulti;

import java.util.List;

public interface PictureSpaceManagement {
    public Uni<List<FileObject>> retrieveAll();
    public Uni<Response> upload(FileFormData fileFormData);
    public RestMulti<Buffer> download(String objectKey);
}
