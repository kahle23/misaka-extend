package misaka.io.oss.support.minio;

import artoria.common.Constants;
import artoria.data.KeyValue;
import artoria.exception.ExceptionUtils;
import artoria.io.oss.OssBase;
import artoria.io.oss.OssInfo;
import artoria.io.oss.OssObject;
import artoria.io.oss.OssStorage;
import artoria.io.oss.support.OssBaseImpl;
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

import java.io.IOException;
import java.io.InputStream;

public class MinioStorage extends AbstractStorage implements OssStorage {
    private static Logger log = LoggerFactory.getLogger(MinioStorage.class);
    private final MinioClient minioClient;
    private final String defaultBucket;
    private final Long partSize;

    public MinioStorage(MinioClient minioClient, Long partSize, String defaultBucket) {
        Assert.notNull(minioClient, "Parameter \"minioClient\" must not null. ");
        if (partSize == null || partSize <= 0) { partSize = 5L * 1024 * 1024; }
        this.defaultBucket = defaultBucket;
        this.minioClient = minioClient;
        this.partSize = partSize;
    }

    public MinioStorage(MinioClient minioClient, Long partSize) {

        this(minioClient, partSize, null);
    }

    public MinioStorage(MinioClient minioClient) {

        this(minioClient, null, null);
    }

    public String getDefaultBucket() {
        Assert.notBlank(defaultBucket, "Parameter \"defaultBucket\" is not set. ");
        return defaultBucket;
    }

    protected OssBase getOssBase(Object key) {
        Assert.notNull(key, "Parameter \"key\" must not null. ");
        if (key instanceof OssBase) {
            OssBase ossBase = (OssBase) key;
            Assert.notBlank(ossBase.getBucketName(), "Parameter \"bucketName\" must not blank. ");
            Assert.notNull(ossBase.getObjectKey(), "Parameter \"objectKey\" must not blank. ");
            return ossBase;
        }
        else if (key instanceof String) {
            OssBaseImpl ossBase = new OssBaseImpl();
            ossBase.setBucketName(getDefaultBucket());
            ossBase.setObjectKey(String.valueOf(key));
            return ossBase;
        }
        else {
            throw new IllegalArgumentException("Parameter \"key\" is not supported. ");
        }
    }

    @Override
    public MinioClient getNative() {

        return minioClient;
    }

    @Override
    public boolean exist(Object key) {
        OssBase ossBase = getOssBase(key);
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
    public OssObject get(Object key) {
        OssBase ossBase = getOssBase(key);
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
        Assert.notNull(data, "Parameter \"data\" must not null. ");
        OssObject ossObject;
        if (data instanceof OssObject) {
            ossObject = (OssObject) data;
        }
        else if (data instanceof KeyValue) {
            try {
                KeyValue keyValue = (KeyValue) data;
                Object key = keyValue.getKey();
                Object value = keyValue.getValue();
                OssBase ossBase = getOssBase(key);
                InputStream inputStream = convertToStream(value, Constants.UTF_8);
                ossObject = new OssObjectImpl(ossBase.getBucketName(), ossBase.getObjectKey());
                ((OssObjectImpl) ossObject).setObjectContent(inputStream);
            }
            catch (IOException e) {
                throw ExceptionUtils.wrap(e);
            }
        }
        else {
            throw new IllegalArgumentException("Parameter \"data\" is not supported. ");
        }
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
    public Object delete(Object key) {
        try {
            OssBase ossBase = getOssBase(key);
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
