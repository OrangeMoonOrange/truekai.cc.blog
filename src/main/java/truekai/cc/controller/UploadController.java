package truekai.cc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import truekai.cc.service.FileService;
import truekai.cc.vo.Result;

@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private FileService fileService;


    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file) {

        String url = fileService.upload(file);
        if (url == null) {
            return Result.fail(1,"文件上传失败");
        }
        return Result.success(url);
    }


}
