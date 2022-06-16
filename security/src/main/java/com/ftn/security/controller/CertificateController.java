package com.ftn.security.controller;

import com.ftn.security.dto.*;
import com.ftn.security.service.CertificateService;
import com.ftn.security.service.ClientService;
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
    private final ClientService clientService;

    @PreAuthorize("hasAuthority('CREATE_ROOT_CERTIFICATE_PERMISSION')")
    @PostMapping("/add-root-certificate")
    public void addRootCertificate(@RequestBody  CreateRootCertificateDto dto){
        loggingService.MakeInfoLog("User " + dto.getAdminMail() + " made root certificate.");
        certificateService.createRootCertificate(dto);
    }

    @PreAuthorize("hasAuthority('CREATE_CERTIFICATES_PERMISSION')")
    @PostMapping("/add-certificate")
    public void addCertificate(@RequestBody CreateCertificateDto dto){
        loggingService.MakeInfoLog("User " + dto.getIssuerMail() + " added certificate.");
        certificateService.createCertificate(dto);
    }



    @PreAuthorize("hasAuthority('GET_CERTIFICATES_PERMISSION')")
    @GetMapping("/getAllUserCertificates")
    public ResponseEntity<ArrayList<CertificateDTO>> getAllUserCertificates(Principal user){
        System.out.println("USER: " + user.getName());
        loggingService.MakeInfoLog("User " + user.getName() + " requested all his certificates.");
        return new ResponseEntity<ArrayList<CertificateDTO>>(certificateService.getAllUserCertificatesDTO(user.getName()), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('REVOKE_CERTIFICATE_PERMISSION')")
    @PostMapping("/revoke-certificate")
    public void revokeCertificate(@RequestBody RevokeCertificateDto revokeCertificateDto){
        loggingService.MakeInfoLog("User " + revokeCertificateDto.getSubjectMail() + " certificate revoked for: " + revokeCertificateDto.getReason()+".");
        certificateService.revokeCertificate(revokeCertificateDto);
    }

    @PreAuthorize("hasAuthority('GET_CERTIFICATES_PERMISSION')")
    @GetMapping("/getAllCertificates")
    public ResponseEntity<ArrayList<CertificateDTO>> gettAllCertificates(){
        loggingService.MakeInfoLog("User " + clientService.getLoggedUserMail() + " requested all certificates.");
        return new ResponseEntity<ArrayList<CertificateDTO>>(certificateService.getAllCertificates(),HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/get-certificate-for-signing")
    public ResponseEntity<?> getCertificatesForSigning(@RequestBody LoadCertificateForSigningDto dto){
    loggingService.MakeInfoLog("User " + clientService.getLoggedUserMail() + " requested certificate for signing.");
        return new ResponseEntity<>(certificateService.getCertificateForSigning(dto),HttpStatus.OK);

    }

    @PreAuthorize("hasAuthority('DOWNLOAD_CERTIFICATE_PERMISSION')")
    @GetMapping("/downloadCertificate/{serialNumber}")
    @ResponseBody
    public ResponseEntity<Resource> downloadCertificate(@PathVariable String serialNumber){
        Resource certDownload = certificateService.downloadCertificateWeb(serialNumber);
        if(certDownload != null){
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + certDownload.getFilename() + "\"")
                    .body(certDownload);
        }
        loggingService.MakeInfoLog("User " + clientService.getLoggedUserMail() + " downloaded certificate.");
        return new ResponseEntity<Resource>(HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasAuthority('GET_CERTIFICATES_FOR_SIGNING_PERMISSION')")
    @PostMapping("/get-user-certificate-for-signing")
    public ResponseEntity<?> getUserCertificatesForSigning(@RequestBody LoadCertificateForSigningDto dto){
        loggingService.MakeInfoLog("User " + clientService.getLoggedUserMail() + " requested certificates for signing.");
        return new ResponseEntity<>(certificateService.getUserCertificateForSigning(dto),HttpStatus.OK);

    }

}
