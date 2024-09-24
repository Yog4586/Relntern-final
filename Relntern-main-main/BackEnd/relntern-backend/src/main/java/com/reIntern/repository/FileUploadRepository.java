package com.reIntern.repository;

import com.reIntern.model.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<FileUpload, Integer> {
    // Custom query methods if needed
}
