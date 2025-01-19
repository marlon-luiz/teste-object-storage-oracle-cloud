package br.com.testeobjectstorageoraclecloud.controller;

import br.com.testeobjectstorageoraclecloud.service.OciObjectStorageService;
import jakarta.servlet.ServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("api/file")
@RestController
public class FileController {
    private final OciObjectStorageService service;

    public FileController(OciObjectStorageService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getFile(@RequestParam("filename") String filename, ServletResponse response) {
        try {
            this.service.downloadFile(filename, response);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            this.service.uploadFile(file);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> removeFile(@RequestParam("filename") String filename) {
        try {
            this.service.removeFile(filename);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
