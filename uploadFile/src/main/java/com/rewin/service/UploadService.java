package com.rewin.service;

import com.rewin.upload.FtpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @Project: uploadFile
 * @author: liusw@rewin.com.cn
 * @Date: 2018/6/5 0005
 * @Copyright: 2018
 * @Version: V1.0
 * Description:
 */
@Service
public class UploadService {
    //  文件上传服务器ip
    @Value("${FTP_REQ_HOST}")
    private String ftpIp;
    //ftp端口
    @Value("${FTP_REQ_PORT}")
    private String ftpPort;
    //用户名
    @Value("${FTP_REQ_USERNAME}")
    private String userName;
    //密码
    @Value("${FTP_REQ_PASSWORD}")
    private String password;
    //文件上传路径
    @Value("${UPLOAD_URL}")
    private String uploadURL;

    /**
     * 文件上传
     * @param is
     * @param fileName
     * @return
     */
    public boolean uploadFile(InputStream is ,String fileName){
        return FtpUtil.storeFile(ftpIp, Integer.parseInt(ftpPort), userName, password, uploadURL, fileName, is);
    }
}
