package br.com.testeobjectstorageoraclecloud.service;

import br.com.testeobjectstorageoraclecloud.config.OsClientConfiguration;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OciObjectStorageService {
    private final OsClientConfiguration configuration;

    @Value("${oci.object-storage.namespace}")
    private String namespace;

    @Value("${oci.object-storage.bucket}")
    private String bucket;

    public OciObjectStorageService(OsClientConfiguration configuration) {
        this.configuration = configuration;
    }

    public void downloadFile(String filename, ServletResponse servletResponse) throws Exception {
        try (var objectStorage = this.configuration.getObjectStorage()) {
            var getObjectRequest = GetObjectRequest.builder()
                    .namespaceName(this.namespace)
                    .bucketName(this.bucket)
                    .objectName(filename)
                    .build();

            var response = objectStorage.getObject(getObjectRequest);
            var inputStream = response.getInputStream();
            inputStream.transferTo(servletResponse.getOutputStream());

            servletResponse.setContentType(response.getContentType());
            servletResponse.setContentLength(response.getContentLength().intValue());
        }
    }

    public void uploadFile(MultipartFile file) throws Exception {
        try (var objectStorage = this.configuration.getObjectStorage()) {
            var putObjectRequest = PutObjectRequest.builder()
                    .namespaceName(this.namespace)
                    .bucketName(this.bucket)
                    .objectName(file.getOriginalFilename())
                    .contentLength(file.getSize())
                    .putObjectBody(file.getInputStream())
                    .build();

            objectStorage.putObject(putObjectRequest);
        }
    }

    public void removeFile(String filename) throws Exception {
        try (var objectStorage = this.configuration.getObjectStorage()) {
            var deleteObjectRequest = DeleteObjectRequest.builder()
                    .namespaceName(this.namespace)
                    .bucketName(this.bucket)
                    .objectName(filename)
                    .build();

            objectStorage.deleteObject(deleteObjectRequest);
        }
    }
}
