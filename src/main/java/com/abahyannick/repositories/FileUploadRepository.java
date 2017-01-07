package com.abahyannick.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abahyannick.DAO.FileUpload;

public interface FileUploadRepository extends JpaRepository<FileUpload, Integer>{

	FileUpload findByFilename(String filename);
}
