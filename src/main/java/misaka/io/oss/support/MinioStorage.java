package misaka.io.oss.support;

import artoria.exception.ExceptionUtils;
import artoria.io.oss.OssBase;
import artoria.io.oss.OssInfo;
import artoria.io.oss.OssObject;
import artoria.io.oss.OssStorage;
import artoria.io.oss.support.OssInfoImpl;
import artoria.io.oss.support.OssObjectImpl;
import artoria.io.storage.AbstractStorage;
import artoria.util.Assert;
import artoria.util.CloseUtils;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.messages.ErrorResponse;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class MinioStorage extends AbstractStorage implements OssStorage {
    private static Logger log = LoggerFactory.getLogger(MinioStorage.class);
    private final MinioClient minioClient;
    private final Long partSize;

    public MinioStorage(MinioClient minioClient, Long partSize) {
        Assert.notNull(minioClient, "Parameter \"minioClient\" must not null. ");
        if (partSize == null) { partSize = 5L * 1024 * 1024; }
        this.minioClient = minioClient;
        this.partSize = partSize;
    }

    @Override
    public MinioClient getNative() {

        return minioClient;
    }

    @Override
    public boolean exist(Object info) {
        OssBase ossBase = (OssBase) info;
        String bucketName = ossBase.getBucketName();
        String objectKey = ossBase.getObjectKey();
        try {
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName).object(objectKey).build());
            return true;
        }
        catch (ErrorResponseException e) {
            ErrorResponse errorResponse = e.errorResponse();
            String code = errorResponse.code();
            if ("NoSuchKey".equals(code)) { return false; }
            else { throw ExceptionUtils.wrap(e); }
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    @Override
    public OssObject get(Object info) {
        OssBase ossBase = (OssBase) info;
        String bucketName = ossBase.getBucketName();
        String objectKey = ossBase.getObjectKey();
        GetObjectResponse objectResponse;
        try {
            objectResponse = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectKey)
                    .build());
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
        if (objectResponse == null) { return null; }
        OssObjectImpl ossObject = new OssObjectImpl();
        ossObject.setBucketName(objectResponse.bucket());
        ossObject.setObjectKey(objectResponse.object());
        ossObject.setMetadata(objectResponse.headers());
        ossObject.setObjectContent(objectResponse);
        return ossObject;
    }

    @Override
    public OssInfo put(Object data) {
        OssObject ossObject = (OssObject) data;
        InputStream inputStream = null;
        try {
            inputStream = ossObject.getObjectContent();
            ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                    .bucket(ossObject.getBucketName())
                    .object(ossObject.getObjectKey())
                    .stream(inputStream, -1, partSize)
                    .build());
            String bucketName = objectWriteResponse.bucket();
            String objectKey = objectWriteResponse.object();
            OssInfoImpl ossInfo = new OssInfoImpl(bucketName, objectKey);
            ossInfo.setOriginal(objectWriteResponse);
            return ossInfo;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
        finally {
            CloseUtils.closeQuietly(inputStream);
        }
    }

    @Override
    public Object delete(Object info) {
        try {
            OssBase ossBase = (OssBase) info;
            String bucketName = ossBase.getBucketName();
            String objectKey = ossBase.getObjectKey();
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectKey)
                    .build());
            return null;
        }
        catch (Exception e) {
            throw ExceptionUtils.wrap(e);
        }
    }

    @Override
    public Object list(Object conditions) {
        // todo minioClient keys
        ListObjectsArgs args = null;
        Iterable<Result<Item>> results = minioClient.listObjects(args);
        return results;
    }

}
