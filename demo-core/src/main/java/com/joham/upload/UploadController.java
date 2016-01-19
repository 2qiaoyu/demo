package com.joham.upload;

import com.joham.util.UtilDate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by joham on 2016/1/18.
 */
@Controller
public class UploadController {

    /**
     * 跳转上传页
     * @return
     */
    @RequestMapping("/toUpload")
    public String toUpload(){
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
    public void upload(MultipartHttpServletRequest request) throws IllegalStateException, IOException {
        MultipartFile mf = request.getFile("uploadFile");
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
        String path = filePath + mf.getOriginalFilename();
        File file = new File(path);
        mf.transferTo(file);
    }
}
