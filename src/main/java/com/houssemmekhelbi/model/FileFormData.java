package com.houssemmekhelbi.model;

import java.io.File;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

public class FileFormData {

    @RestForm("file")
    public File data;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String filename;

    @RestForm
    @PartType(MediaType.TEXT_PLAIN)
    public String mimetype;
}
