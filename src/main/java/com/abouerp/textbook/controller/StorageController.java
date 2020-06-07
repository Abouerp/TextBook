package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.domain.Storage;
import com.abouerp.textbook.service.FileStorageService;
import com.abouerp.textbook.service.StorageService;
import lombok.extern.log4j.Log4j2;
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
     * @return  一个list存储图片的名称（id）
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
     * @param id   图片/视频的名称id
     */
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable String id) {
        Storage storage = storageService.findBySHA1(id);
        return ResponseEntity.ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=\"%s\"", storage.getOriginalFilename())
        ).body(fileStorageService.findByHash(id));
    }
}
