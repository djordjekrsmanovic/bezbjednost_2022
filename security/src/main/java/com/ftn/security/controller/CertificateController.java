package com.ftn.security.controller;

import com.ftn.security.dto.CreateCertificateDto;
import com.ftn.security.dto.CreateRootCertificateDto;
import com.ftn.security.service.CertificateService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/add-certificate")
    public void addCertificate(@RequestBody CreateCertificateDto dto){
        certificateService.createCertificate(dto);
    }

    @GetMapping("/get")
    public int get(){
        return 100;
    }

}
