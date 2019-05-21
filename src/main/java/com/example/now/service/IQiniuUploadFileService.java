package com.example.now.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;
import java.io.InputStream;


/**
 * Qiniu upload file(image) service class
 * prepared for extending(not used now)
 *
 * @author qsw
 * @date 2019/05/17
 */
public interface IQiniuUploadFileService {
    /**
     * upload file
     *
     * @param file file
     * @param key key
     * @return 返回值说明：包含是否成功上传的信息的response
     * @throws QiniuException QiniuException
     */
    Response uploadFile(File file,String key) throws QiniuException;

    /**
     * upload file
     *
     * @param inputStream inputStream
     * @param key key
     * @return 返回值说明：包含是否成功上传的信息的response
     * @throws QiniuException QiniuException
     */
    Response uploadFile(InputStream inputStream,String key) throws QiniuException;

    /**
     * delete file
     *
     * @param key key
     * @return 返回值说明：包含是否成功上传的信息的response
     * @throws  QiniuException QiniuException
     */
    Response delete(String key) throws QiniuException;
}
