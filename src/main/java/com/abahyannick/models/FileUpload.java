package com.abahyannick.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Table(name = "fileupload")
@Entity
@Component
public class FileUpload {

    public FileUpload(String filename, String mimeType) {

        this.filename = filename;
        this.mimeType = mimeType;
    }

    public FileUpload() {
        // Default Constructor
    }
    
    @Id
    @Column(name = "filename")
    private String filename;
    
    @Column(name = "mimeType")
    private String mimeType;
    

    
    



	public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }


    
}