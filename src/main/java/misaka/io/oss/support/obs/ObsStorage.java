package misaka.io.oss.support.obs;

import artoria.common.Constants;
import artoria.data.KeyValue;
import artoria.data.bean.BeanUtils;
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
import com.obs.services.ObsClient;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * https://support.huaweicloud.com/productdesc-obs/obs_03_0370.html
 */
public class ObsStorage extends AbstractStorage implements OssStorage {
    private static Logger log = LoggerFactory.getLogger(ObsStorage.class);
    private final ObsClient obsClient;
    private final String defaultBucket;

    public ObsStorage(ObsClient obsClient, String defaultBucket) {
        Assert.notNull(obsClient, "Parameter \"obsClient\" must not null. ");
        this.defaultBucket = defaultBucket;
        this.obsClient = obsClient;
    }

    public ObsStorage(ObsClient obsClient) {

        this(obsClient, null);
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
    public ObsClient getNative() {

        return obsClient;
    }

    @Override
    public OssObject get(Object key) {
        OssBase ossBase = getOssBase(key);
        String bucketName = ossBase.getBucketName();
        String objectKey = ossBase.getObjectKey();
        ObsObject obsObject = obsClient.getObject(bucketName, objectKey);
        if (obsObject == null) { return null; }
        return BeanUtils.beanToBean(obsObject, OssObjectImpl.class);
    }

    @Override
    public boolean exist(Object key) {
        OssBase ossBase = getOssBase(key);
        String bucketName = ossBase.getBucketName();
        String objectKey = ossBase.getObjectKey();
        return obsClient.doesObjectExist(bucketName, objectKey);
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
            PutObjectResult putObjectResult = obsClient.putObject(ossObject.getBucketName(), ossObject.getObjectKey(), inputStream);
            String bucketName = putObjectResult.getBucketName();
            String objectKey = putObjectResult.getObjectKey();
            String objectUrl = putObjectResult.getObjectUrl();
            OssInfoImpl ossInfo = new OssInfoImpl(bucketName, objectKey, objectUrl);
            ossInfo.setOriginal(putObjectResult);
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
        OssBase ossBase = getOssBase(key);
        String bucketName = ossBase.getBucketName();
        String objectKey = ossBase.getObjectKey();
        return obsClient.deleteObject(bucketName, objectKey);
    }

    @Override
    public Object list(Object conditions) {
        // todo obsClient listObjects
        // pattern
        String keyString = (String) conditions;
        ObjectListing objectListing = obsClient.listObjects(keyString);
        return objectListing.getObjects();
    }

}
