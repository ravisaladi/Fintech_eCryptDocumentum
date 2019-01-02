package com.rkvs.stBlockCh;

import javax.servlet.http.HttpSession;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.rkvs.stBlockCh.utilities.ApplicatonGlobalConfig;
import com.rkvs.stBlockCh.utilities.StorageProperties;
import com.rkvs.stBlockCh.utilities.StorageService;

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
