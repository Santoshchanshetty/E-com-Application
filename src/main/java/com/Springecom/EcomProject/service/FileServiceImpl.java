package com.Springecom.EcomProject.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

//This was created after creation of updateImage in Product service Impl
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //File name of Current / original file
        String originalFilename=file.getOriginalFilename();
        //Generate Unique filename
        String randomId= UUID.randomUUID().toString();
        String filename=randomId.concat(originalFilename.substring(originalFilename.lastIndexOf('.')));
        String filePath=path+ File.separator+filename;
        //Check if path exist or not or create one
        File folder=new File(path);
        if(!folder.exists()){
            folder.mkdirs();
        }
        //Upload to server
        Files.copy(file.getInputStream(), Paths.get(filePath));

        //Returning filename
        return filename;
    }
}
