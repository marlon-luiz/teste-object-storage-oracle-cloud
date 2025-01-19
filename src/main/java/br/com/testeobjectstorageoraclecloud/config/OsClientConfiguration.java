package br.com.testeobjectstorageoraclecloud.config;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class OsClientConfiguration {
    @Value("${oci.config-file}")
    private String configurationFilePath;

    @Value("${oci.profile}")
    private String profile;

    public ObjectStorage getObjectStorage() throws IOException {
        //load config file
        final var configFile = ConfigFileReader.parse(configurationFilePath, profile);

        final var provider = new ConfigFileAuthenticationDetailsProvider(configFile);

        //build and return client
        return ObjectStorageClient.builder().build(provider);
    }
}
