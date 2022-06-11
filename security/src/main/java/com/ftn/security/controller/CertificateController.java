package com.ftn.security.controller;

import com.ftn.security.dto.*;
import com.ftn.security.service.CertificateService;
import com.ftn.security.service.LoggingService;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

@RestController()
@RequestMapping("api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;
    private final LoggingService loggingService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-root-certificate")
    public void addRootCertificate(@RequestBody  CreateRootCertificateDto dto){
        loggingService.MakeInfoLog("User " + dto.getAdminMail() + " made root certificate.");
        certificateService.createRootCertificate(dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/add-certificate")
    public void addCertificate(@RequestBody CreateCertificateDto dto){
        certificateService.createCertificate(dto);
    }



    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/getAllUserCertificates")
    public ResponseEntity<ArrayList<CertificateDTO>> getAllUserCertificates(Principal user){
        System.out.println("USER: " + user.getName());
        loggingService.MakeInfoLog("User " + user.getName() + " requested all his certificates.");
        return new ResponseEntity<ArrayList<CertificateDTO>>(certificateService.getAllUserCertificatesDTO(user.getName()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/revoke-certificate")
    public void revokeCertificate(@RequestBody RevokeCertificateDto revokeCertificateDto){
        loggingService.MakeInfoLog("User " + revokeCertificateDto.getSubjectMail() + " certificate revoked for: " + revokeCertificateDto.getReason()+".");
        certificateService.revokeCertificate(revokeCertificateDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/getAllCertificates")
    public ResponseEntity<ArrayList<CertificateDTO>> gettAllCertificates(){
        return new ResponseEntity<ArrayList<CertificateDTO>>(certificateService.getAllCertificates(),HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/get-certificate-for-signing")
    public ResponseEntity<?> getCertificatesForSigning(@RequestBody LoadCertificateForSigningDto dto){

        return new ResponseEntity<>(certificateService.getCertificateForSigning(dto),HttpStatus.OK);

    }

    @GetMapping("/downloadCertificate/{serialNumber}")
    @ResponseBody
    public ResponseEntity<Resource> downloadCertificate(@PathVariable String serialNumber){
        Resource certDownload = certificateService.downloadCertificateWeb(serialNumber);
        if(certDownload != null){
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + certDownload.getFilename() + "\"")
                    .body(certDownload);
        }
        return new ResponseEntity<Resource>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/get-user-certificate-for-signing")
    public ResponseEntity<?> getUserCertificatesForSigning(@RequestBody LoadCertificateForSigningDto dto){

        return new ResponseEntity<>(certificateService.getUserCertificateForSigning(dto),HttpStatus.OK);

    }

}
