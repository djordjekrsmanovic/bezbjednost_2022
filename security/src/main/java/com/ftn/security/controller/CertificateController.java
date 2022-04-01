package com.ftn.security.controller;

import com.ftn.security.dto.CreateRootCertificateDto;
import com.ftn.security.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

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

    //@PreAuthorize("hasRole('USER')")
    @GetMapping("/getAllUserCertificates")
    public ResponseEntity<ArrayList<X509Certificate>> getAllUserCertificates(Principal user){
        System.out.println("USER: " + user.getName());
        return new ResponseEntity<ArrayList<X509Certificate>>(certificateService.getAllUserCertificates(user.getName()), HttpStatus.OK);
    }

}
