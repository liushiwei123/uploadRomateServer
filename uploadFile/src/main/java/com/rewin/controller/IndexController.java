package com.rewin.controller;

import com.rewin.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @Project: uploadFile
 * @author: liusw@rewin.com.cn
 * @Date: 2018/6/4 0004
 * @Copyright: 2018
 * @Version: V1.0
 * Description:
 */
@Controller
public class IndexController {
    @Autowired
    private UploadService uploadService ;

    @RequestMapping(value = "/index" ,method = RequestMethod.GET)
    public String index(){
        return "index" ;
    }

    /**
     *   文件上传
     * @param file
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file,HttpServletRequest request,HttpServletResponse response )  {
        InputStream is = null;
        String filename = null;
        boolean success = false ;
        try {
            //获取输入流
            is = file.getInputStream();
            // 获取文件名
             filename = file.getOriginalFilename();
             success = uploadService.uploadFile(is,filename);
        } catch (IOException e) {
            e.printStackTrace();
            return  "fail";
        }
        if(success){
            return "success";
        }else {
            return "fail";
        }
    }
}
