package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.port.output.FileStoragePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Tag(name = "File Upload", description = "File upload and download APIs")
public class FileUploadController {

    private final FileStoragePort fileStoragePort;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Upload a file")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "directory", defaultValue = "general") String directory) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String filePath = fileStoragePort.storeFile(file, directory);

        Map<String, String> response = new HashMap<>();
        response.put("message", "File uploaded successfully");
        response.put("filePath", filePath);
        response.put("fileName", file.getOriginalFilename());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload/avatar")
    @Operation(summary = "Upload user avatar")
    public ResponseEntity<Map<String, String>> uploadAvatar(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Validate image file
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        String filePath = fileStoragePort.storeFile(file, "avatars");

        Map<String, String> response = new HashMap<>();
        response.put("message", "Avatar uploaded successfully");
        response.put("avatarUrl", "/api/v1/files/download/" + filePath);
        response.put("filePath", filePath);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload/product")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Upload product image")
    public ResponseEntity<Map<String, String>> uploadProductImage(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        // Validate image file
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        String filePath = fileStoragePort.storeFile(file, "products");

        Map<String, String> response = new HashMap<>();
        response.put("message", "Product image uploaded successfully");
        response.put("imageUrl", "/api/v1/files/download/" + filePath);
        response.put("filePath", filePath);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{directory}/{filename:.+}")
    @Operation(summary = "Download a file")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String directory,
            @PathVariable String filename) {

        try {
            String filePath = directory + "/" + filename;

            if (!fileStoragePort.fileExists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            Path file = Paths.get(System.getProperty("user.dir"), "uploads", filePath);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{directory}/{filename:.+}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a file")
    public ResponseEntity<Map<String, String>> deleteFile(
            @PathVariable String directory,
            @PathVariable String filename) {

        String filePath = directory + "/" + filename;

        if (!fileStoragePort.fileExists(filePath)) {
            throw new IllegalArgumentException("File not found");
        }

        fileStoragePort.deleteFile(filePath);

        Map<String, String> response = new HashMap<>();
        response.put("message", "File deleted successfully");

        return ResponseEntity.ok(response);
    }
}
