package com.pjz.fileupload.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileUploadController {

    @Value("${upload.temp-dir}")
    private String tempDir;

    @Value("${upload.final-dir}")
    private String finalDir;

    @GetMapping("/check")
    public List<Integer> check(@RequestParam("fileHash") String fileHash) {
        //拼接出完整的父级目录
        String folderPath = tempDir + "/" + fileHash;
        File folder = new File(folderPath);
        List<Integer> fileChunks = new ArrayList<>();
        if (folder.exists() && folder.isDirectory()) {
            //返回所有分片的index
            fileChunks = Arrays.stream(Objects.requireNonNull(folder.listFiles()))
                    .map(file -> file.getName().split("\\.")[0])
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
        return fileChunks;
    }

    @PostMapping("/chunkUpload")
    public String chunkUpload(
            @RequestPart("chunk") MultipartFile chunk,
            @RequestPart("fileIndex") Integer chunkIndex,
            @RequestPart("fileHash") String fileHash) throws Exception {
        //拼接父级目录
        String folderPath = tempDir + "/" + fileHash;
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //将分片保存到某个目录下
        //拼接分片的保存路径
        String chunkPath = folderPath + "/" + chunkIndex;
        chunk.transferTo(new File(chunkPath));
        return "success";
    }

    @PostMapping("/merge")
    public String merge(@RequestParam("fileHash") String fileHash, @RequestParam("fileName") String fileName, @RequestParam("totalChunks") Integer totalChunks) throws Exception {
        //拼接分片保存的父级目录
        String folderPath = tempDir + "/" + fileHash;
        File folder = new File(folderPath);
        //判断分片文件是否有效
        if (!folder.exists() || Objects.requireNonNull(folder.listFiles()).length != totalChunks) {
            return "分片文件不完整，无法合并";
        }

        //拼接合并文件的保存路径
        String finalPath = finalDir + "/" + fileName;
        try (
                FileOutputStream out = new FileOutputStream(finalPath)
        ) {
            for (int i = 0; i < totalChunks; i++) {
                //将分片按顺序合并
                File chunk = new File(folderPath + "/" + i);
                Files.copy(chunk.toPath(), out);
                //合并后删除分片
                chunk.delete();
            }
        }
        return "success";
    }
}
