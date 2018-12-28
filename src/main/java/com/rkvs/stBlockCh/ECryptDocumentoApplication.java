package com.rkvs.stBlockCh;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.bessapps.stBlockCh.utilities.ApplicatonGlobalConfig;
import com.bessapps.stBlockCh.utilities.StorageProperties;
import com.rkvs.utilities.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ECryptDocumentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECryptDocumentoApplication.class, args);
		ApplicatonGlobalConfig.initializeApplication();
	}

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
