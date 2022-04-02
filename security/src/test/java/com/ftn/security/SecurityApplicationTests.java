package com.ftn.security;

import com.ftn.security.service.CertificateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.cert.X509Certificate;

@SpringBootTest
class SecurityApplicationTests {

	@Test
	void contextLoads() {
		System.out.println("heloloooo testing ");
	}


	@Autowired
	private CertificateService certificateService;

	@Test
	void testGetX509CertificateBySerialNumber(){

		X509Certificate certificate = certificateService.getX509CertificateBySerialNumber("7148004613218253201");
		if(certificate != null){
			System.out.println("Pronadjen sertifikat:" + certificate.getSerialNumber().toString());
		}else{
			System.out.println("ERROR NIJE PRONADJEN SERTIFIAKT");
		}

	}

}
