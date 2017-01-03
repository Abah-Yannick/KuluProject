package com.abahyannick.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abahyannick.models.FileUpload;
import com.abahyannick.repositories.FileUploadRepository;

@Service
public class FileUploadService {

    @Autowired
    FileUploadRepository fileUploadRepository;

    // Retrieve file
    public FileUpload findByFilename(String filename) {
        return fileUploadRepository.findByFilename(filename);
    }

    // Upload the file
    public void uploadFile(FileUpload doc) {
        fileUploadRepository.saveAndFlush(doc);
    }
    
    public FileUpload save(FileUpload fileUpload){
    	return fileUploadRepository.save(fileUpload);
    }
}
