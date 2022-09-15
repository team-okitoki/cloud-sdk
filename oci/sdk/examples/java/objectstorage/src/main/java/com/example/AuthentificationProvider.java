package com.example;

import com.google.common.base.Supplier;
import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.ConfigFileReader.ConfigFile;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimplePrivateKeySupplier;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AuthentificationProvider {

    public AuthenticationDetailsProvider getAuthenticationDetailsProvider() throws IOException {

        ClassLoader classLoader = AuthentificationProvider.class.getClassLoader();
        File tempConfigFile = new File(classLoader.getResource("config").getFile());
        File tempOCIAPIKey = new File(classLoader.getResource("oci_api_key.pem").getFile());

        ConfigFile config = ConfigFileReader.parse(tempConfigFile.getPath(), "DEFAULT");

        Supplier<InputStream> privateKeySupplier = new SimplePrivateKeySupplier(tempOCIAPIKey.getPath());

        AuthenticationDetailsProvider provider = SimpleAuthenticationDetailsProvider.builder()
                .tenantId(config.get("tenancy")).userId(config.get("user")).fingerprint(config.get("fingerprint"))
                .privateKeySupplier(privateKeySupplier).region(Region.AP_SEOUL_1).build();

        return provider;
    }

}