package com.joham.upload;

import com.joham.util.UtilDate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by joham on 2016/1/18.
 */
@Controller
public class UploadController {

    /**
     * 跳转上传页
     *
     * @return
     */
    @RequestMapping("/toUpload")
    public String toUpload() {
        return "jsp/upload";
    }

    /**
     * 上传
     *
     * @param request
     * @throws java.io.IOException
     * @throws IllegalStateException
     */
    @RequestMapping("/upload")
    public void upload(HttpServletRequest request) throws IllegalStateException, IOException {
        // 转型为MultipartHttpRequest：
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //获得文件
        MultipartFile mf = multipartRequest.getFile("uploadFile");
        //判断上传文件是否符合标准
        if(!checkFileForUpload(mf)){
            System.out.println("格式或大小错误");
            return;
        }
        // 根据系统时间生成上传后保存的文件名
        String filePath = "D:/upload/"
                + UtilDate.todayFormatString(new Date()) + "/";
        File picSaveFile = new File(filePath);
        // 根据真实路径创建目录文件
        if (!picSaveFile.exists()) {
            try {
                picSaveFile.mkdirs();
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
        }
        //重命名图片路径
        String path = filePath + UUID.randomUUID().toString() + System.currentTimeMillis() + ".jpg";
        File file = new File(path);
        mf.transferTo(file);
        System.out.println(path);
    }

    /**
     * 判断图片是否符合要求
     * @param muFile
     * @return
     */
    private static boolean checkFileForUpload(MultipartFile muFile) {
        boolean bool = true;
        // 检查文件大小
        if (muFile.getSize() > 5 * 1024 * 1024) {
            //超过大小限制
            bool = false;
        }
        String fileName = muFile.getOriginalFilename();
        // 检查扩展名
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        //定义允许上传的文件类型
        List<String> fileTypes = new ArrayList<String>();
        fileTypes.add(".jpg");
        fileTypes.add(".jpeg");
        fileTypes.add(".gif");
        fileTypes.add(".png");
        if (!fileTypes.contains(fileExt.toLowerCase())) {
            //图片格式不对
            bool = false;
        }
        return bool;
    }
}
