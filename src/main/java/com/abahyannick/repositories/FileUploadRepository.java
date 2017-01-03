package com.abahyannick.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abahyannick.models.FileUpload;

public interface FileUploadRepository extends JpaRepository<FileUpload, Integer>{

	FileUpload findByFilename(String filename);
}
