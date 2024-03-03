package com.houssemmekhelbi.utils;

import com.houssemmekhelbi.model.FileObject;
import io.vertx.core.buffer.Buffer;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;

import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PictureManagementFunction {

    /**
     * Converts a ByteBuffer to a Vert.x Buffer.
     * @param bytebuffer The ByteBuffer to convert.
     * @return A Vert.x Buffer containing the data from the ByteBuffer.
     */
    public static Buffer toBuffer(ByteBuffer bytebuffer) {
        byte[] result = new byte[bytebuffer.remaining()];
        bytebuffer.get(result);
        return Buffer.buffer(result);
    }

    /**
     * Converts a ListObjectsResponse to a list of FileObject.
     * @param objects The ListObjectsResponse containing file objects.
     * @return A list of FileObject representing the files in the ListObjectsResponse.
     */
    public static List<FileObject> toFileItems(ListObjectsResponse objects) {
        return objects.contents().stream()
                .map(FileObject::from)
                .sorted(Comparator.comparing(FileObject::getObjectKey))
                .collect(Collectors.toList());
    }
}
