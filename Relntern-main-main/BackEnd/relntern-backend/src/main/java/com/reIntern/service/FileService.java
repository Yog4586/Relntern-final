//package com.reIntern.service;
//
//import com.reIntern.model.FileEntity;
//import com.reIntern.repository.FileRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.transaction.annotation.Transactional; 
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class FileService {
//
//    @Value("${file.upload-dir}")  // Configured in application.properties
//    private String uploadDir;
//
//    @Autowired
//    private FileRepository fileRepository;
//
//    // Save files to the local file system and database
//    public void saveFiles(List<MultipartFile> files, Long internId) throws IOException {
//        for (MultipartFile file : files) {
//            saveFile(file, internId);
//        }
//    }
//
//    // Save a single file to the local file system and database
//    public void saveFile(MultipartFile file, Long internId) throws IOException {
//        if (file.isEmpty()) {
//            throw new IOException("Cannot save empty file");
//        }
//
//        // Ensure the directory exists
//        File dir = new File(uploadDir);
//        if (!dir.exists()) {
//            if (!dir.mkdirs()) {
//                throw new IOException("Failed to create directory: " + uploadDir);
//            }
//        }
//
//        // Build the file path
//        Path path = Paths.get(uploadDir, file.getOriginalFilename());
//
//        // Save the file locally
//        Files.write(path, file.getBytes());
//
//        // Save file metadata to the database
//        FileEntity fileEntity = new FileEntity(file.getOriginalFilename(), file.getSize(), file.getBytes(), internId);
//        fileRepository.save(fileEntity);
//    }
//
//    // Get all file names stored in the local directory
//    public List<String> getAllFileNames() {
//        try {
//            return Files.list(Paths.get(uploadDir))
//                    .map(Path::getFileName)
//                    .map(Path::toString)
//                    .collect(Collectors.toList());
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to read files from storage directory", e);
//        }
//    }
//
//    // Retrieve a specific file by name
//    public Path getFile(String fileName) {
//        Path filePath = Paths.get(uploadDir, fileName);
//        if (Files.exists(filePath) && Files.isReadable(filePath)) {
//            return filePath;
//        } else {
//            throw new RuntimeException("File not found or not readable: " + fileName);
//        }
//    }
//
//    // Delete a file from the local storage and the database
//    @Transactional
//    public void deleteFile(String fileName) throws IOException {
//        Path filePath = Paths.get(uploadDir, fileName);
//        if (Files.exists(filePath)) {
//            Files.delete(filePath);
//            // Delete the file entry from the database
//            fileRepository.deleteByFileName(fileName);
//        } else {
//            throw new IOException("File not found: " + fileName);
//        }
//    }
//}
