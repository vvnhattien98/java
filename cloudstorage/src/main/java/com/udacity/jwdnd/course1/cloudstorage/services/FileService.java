package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public List<File> getAllFileByUserId(Integer id){
        return fileMapper.getFileByUserId(id);
    }

    public File getFileByName(String fileName){
        return fileMapper.getFile(fileName);
    }

    public void deleteFile(Integer fileId){
        fileMapper.deleteFileId(fileId);
    }

    public int addFile(MultipartFile multipartFile, String userName) throws IOException{
        InputStream inputStream = multipartFile.getInputStream();

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;

        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        buffer.flush();
        byte[] fileData = buffer.toByteArray();

        String fileName =multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        Integer userId =userMapper.getUser(userName).getUserId();

        return fileMapper.addFile(new File(0,fileName,contentType,fileSize,userId,fileData));
    }


}
