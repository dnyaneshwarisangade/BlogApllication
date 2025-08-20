package com.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException{

        // file name
        String name = file.getOriginalFilename();
        // abc.png

        // random name generate file
        String randomId = UUID.randomUUID().toString();
        String filename1 = randomId.concat(name.substring(name.lastIndexOf(".")));

        // file path
        String filepath = path + File.separator + filename1;

        // create folder if not created
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        // file copy
        Files.copy(file.getInputStream(), Paths.get(filepath));

        return filename1;

    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullpath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullpath);
        // db logic to return input stream
        return is;
    }

}
