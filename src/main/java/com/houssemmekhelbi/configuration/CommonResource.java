package com.houssemmekhelbi.configuration;

import com.houssemmekhelbi.model.FileFormData;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

abstract public class CommonResource {

    @ConfigProperty(name = "bucket.name")
    public String bucketName;

    protected PutObjectRequest buildPutRequest(FileFormData fileFormData) {
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileFormData.filename)
                .contentType(fileFormData.mimetype)
                .build();
    }

    protected GetObjectRequest buildGetRequest(String objectKey) {
        return GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
    }
}