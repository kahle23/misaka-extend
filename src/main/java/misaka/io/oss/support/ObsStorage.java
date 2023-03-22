package misaka.io.oss.support;

import artoria.data.bean.BeanUtils;
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
import com.obs.services.ObsClient;
import com.obs.services.model.ObjectListing;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * https://support.huaweicloud.com/productdesc-obs/obs_03_0370.html
 */
public class ObsStorage extends AbstractStorage implements OssStorage {
    private static Logger log = LoggerFactory.getLogger(ObsStorage.class);
    private final ObsClient obsClient;

    public ObsStorage(ObsClient obsClient) {
        Assert.notNull(obsClient, "Parameter \"obsClient\" must not null. ");
        this.obsClient = obsClient;
    }

    @Override
    public ObsClient getNative() {

        return obsClient;
    }

    @Override
    public OssObject get(Object info) {
        OssBase ossBase = (OssBase) info;
        String bucketName = ossBase.getBucketName();
        String objectKey = ossBase.getObjectKey();
        ObsObject obsObject = obsClient.getObject(bucketName, objectKey);
        if (obsObject == null) { return null; }
        return BeanUtils.beanToBean(obsObject, OssObjectImpl.class);
    }

    @Override
    public boolean exist(Object info) {
        OssBase ossBase = (OssBase) info;
        String bucketName = ossBase.getBucketName();
        String objectKey = ossBase.getObjectKey();
        return obsClient.doesObjectExist(bucketName, objectKey);
    }

    @Override
    public OssInfo put(Object data) {
        OssObject ossObject = (OssObject) data;
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
    public Object delete(Object info) {
        OssBase ossBase = (OssBase) info;
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
