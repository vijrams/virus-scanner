package com.wkelms.clamav


import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
class VirusScannerApplication {

	static void main(String[] args) {
		SpringApplication.run(VirusScannerApplication, args)
	}

}
