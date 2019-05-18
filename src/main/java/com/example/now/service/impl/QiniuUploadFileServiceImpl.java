package com.example.now.service.impl;

import com.example.now.service.IQiniuUploadFileService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

/**
 * Qiniu upload file(image) service implementation class
 * prepared for extending(not used now)
 *
 * @author qsw
 * @date 2019/05/17
 */
@Service
public class QiniuUploadFileServiceImpl implements IQiniuUploadFileService,InitializingBean {
    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    @Value("${qiniu.bucket}")
    private String bucket;


    private StringMap putPolicy;

    @Override
    public Response uploadFile(File file,String key) throws QiniuException {
        Response response = this.uploadManager.put(file, key, getUploadToken());
        int retry = 0;
        int maxRetry = 3;
        while (response.needRetry() && retry < maxRetry) {
            response = this.uploadManager.put(file, key, getUploadToken());
            retry++;
        }
        return response;
    }

    @Override
    public Response uploadFile(InputStream inputStream,String key) throws QiniuException {
        Response response = this.uploadManager.put(inputStream, key, getUploadToken(), null, null);
        int retry = 0;
        int maxRetry = 3;
        while (response.needRetry() && retry < maxRetry) {
            response = this.uploadManager.put(inputStream, key, getUploadToken(), null, null);
            retry++;
        }
        return response;
    }

    @Override
    public Response delete(String key) throws QiniuException {
        Response response = bucketManager.delete(this.bucket, key);
        int retry = 0;
        int maxRetry = 3;
        while (response.needRetry() && retry++ < maxRetry) {
            response = bucketManager.delete(bucket, key);
        }
        return response;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
    }

    private String getUploadToken() {
        return this.auth.uploadToken(bucket, null, 3600, putPolicy);
    }
}

