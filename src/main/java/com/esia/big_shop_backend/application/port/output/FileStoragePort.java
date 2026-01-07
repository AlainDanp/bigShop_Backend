package com.esia.big_shop_backend.application.port.output;
import org.springframework.web.multipart.MultipartFile;

public interface FileStoragePort {
    String storeFile(MultipartFile file, String directory);
    void deleteFile(String filePath);
    boolean fileExists(String filePath);
}
