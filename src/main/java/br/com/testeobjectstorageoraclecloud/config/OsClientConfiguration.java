package br.com.testeobjectstorageoraclecloud.config;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class OsClientConfiguration {
    // Path to OCI config file
    String configurationFilePath = "C:\\Users\\marlo\\Documents\\Projetos\\teste-object-storage-oracle-cloud\\config";
    String profile = "DEFAULT";

    public ObjectStorage getObjectStorage() throws IOException {
        //load config file
        final var configFile = ConfigFileReader.parse(configurationFilePath, profile);

        final var provider = new ConfigFileAuthenticationDetailsProvider(configFile);

        //build and return client
        return ObjectStorageClient.builder().build(provider);
    }
}
