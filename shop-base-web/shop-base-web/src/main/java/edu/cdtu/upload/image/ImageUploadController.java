package edu.cdtu.upload.image;

import edu.cdtu.utils.ResultUtils;
import edu.cdtu.utils.ResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

//图片上传
@RestController
@RequestMapping("/api/upload")
public class ImageUploadController {
//图片上传的路径 与application-test.yml配置的一致
    @Value("${web.uploadpath}")
    private String webUploadpath;

    @RequestMapping("/uploadImage")
    public ResultVo uploadImage(@RequestParam("file") MultipartFile file) {
        String Url = "";
//图片原名
        String fileName = file.getOriginalFilename();
        //获取文件扩展名，即除后缀名外
        String fileExtenionName = fileName.substring(fileName.indexOf("."));
        //新的文件名，避免重名覆盖,生成uuid
        String newName = UUID.randomUUID() + fileExtenionName;
//  图片上传路径
        String path = webUploadpath;
//  路径转换成File类型
        File fileDir = new File(path);
//  如果路径文件不存在，创建文件，并设置可编辑
        if (!fileDir.exists()) {
            fileDir.mkdirs();
            //设置权限
            fileDir.setWritable(true);
        }
//  整个新文件，不只是文件夹名，加上了路径
        File targetFile = new File(path, newName);
        try {
//  上传文件
            file.transferTo(targetFile);
            Url = "/" + targetFile.getName();
        } catch (Exception e) {
            return null;
        }
        return ResultUtils.success("成功", "/images" + Url);
    }
}