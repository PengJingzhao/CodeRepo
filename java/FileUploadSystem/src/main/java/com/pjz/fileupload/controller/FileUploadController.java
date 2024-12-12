package com.pjz.fileupload.controller;

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
public class FileUploadController {

    @GetMapping("/check")
    public List<Integer> check(@RequestParam("fileHash") String fileHash) {
        String folderPath = "";
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
            @RequestParam("chunk") MultipartFile chunk,
            @RequestParam("fileIndex") Integer fileIndex,
            @RequestParam("fileHash") String fileHash) throws Exception{
        String savePath = "";
        //将分片保存到某个目录下
        chunk.transferTo(new File(savePath));
        return "success";
    }

    @PostMapping("/merge")
    public String merge(@RequestParam("fileHash")String fileHash, @RequestParam("fileName")String fileName,@RequestParam("totalChunks")Integer totalChunks) throws Exception {
       String finalPath = "";
       String folderPath = "";
       try (
               FileOutputStream out = new FileOutputStream(finalPath)
       ){
           for (int i = 0; i < totalChunks; i++) {
               //将分片按顺序合并
               File chunk = new File(folderPath + "/" + i);
               Files.copy(chunk.toPath(), out);
           }
       }
        return "success";
    }
}
