package com.ftn.security.controller;

import com.ftn.security.dto.CreateRootCertificateDto;
import com.ftn.security.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;


    @PostMapping("/add-root-certificate")
    public void addRootCertificate(@RequestBody  CreateRootCertificateDto dto){
        certificateService.createRootCertificate(dto);
    }

    @GetMapping("/get")
    public int get(){
        return 100;
    }

}
