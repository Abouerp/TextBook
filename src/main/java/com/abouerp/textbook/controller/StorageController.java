package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.domain.Storage;
import com.abouerp.textbook.service.FileStorageService;
import com.abouerp.textbook.service.StorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件处理
 *
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/storage")
@Log4j2
public class StorageController {

    private final StorageService storageService;
    private final FileStorageService fileStorageService;

    public StorageController(StorageService storageService, FileStorageService fileStorageService) {
        this.storageService = storageService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * 保存图片或者视频
     *
     * @param files
     * @return 一个list存储图片的名称（id）
     */
    @PostMapping
    public ResultBean<List<String>> save(@RequestParam("files") MultipartFile[] files) {
        List<String> picList = new ArrayList<>();
        for (MultipartFile multipartFile : files) {
            String sha = fileStorageService.upload(multipartFile);
            if (storageService.findBySHA1(sha) == null) {
                storageService.save(sha, multipartFile);
            }
            picList.add(sha);
        }
        return new ResultBean<>(picList);
    }

    /**
     * 前端或者预览图片或者视频用此接口
     *
     * @param id 图片/视频存储在数据库的sha1
     */
    @GetMapping(value = "/preview/{id}")
    public ResponseEntity<Resource> preview(@PathVariable String id) {
        Storage storage = storageService.findBySHA1(id);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, storageService.findBySHA1(id).getContentType())
                .body(fileStorageService.findByHash(storage.getSha1()));
    }


    /**
     * 触发浏览器下载
     *
     * @param id 图片/视频的名称id
     */
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable String id) {
        Storage storage = storageService.findBySHA1(id);
        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", storage.getOriginalFilename())
//                String.format("attachment; filename=\"%s\"", new String(storage.getOriginalFilename().getBytes("utf-8"),"ISO8859-1"))
        ).body(fileStorageService.findByHash(id));
        /**
         * 如果上面方法下载文件之后出现中文变成下划线，就用注释的代码
         * String的getBytes()方法是得到一个操作系统默认的编码格式的字节数组
         * String.getBytes(String decode1)方法会根据指定的decode编码返回某字符串在该编码下的byte数组
         * new String(byte[],decode2)方法是 使用decode2编码来还原byte数组
         */
    }


    @GetMapping("/download/user-model")
    public ResponseEntity<Resource> getModel() {
        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", "model.xls")
        ).body(new ClassPathResource("excel/admin_model.xls"));
    }
}
