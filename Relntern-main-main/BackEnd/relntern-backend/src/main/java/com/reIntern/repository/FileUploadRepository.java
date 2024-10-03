package com.reIntern.repository;

import com.reIntern.model.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<FileUpload, Integer> {
    Optional<FileUpload> findByFileName(String fileName);
}
