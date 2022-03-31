package com.ftn.security.dto;

import com.ftn.security.model.ExtendedKeyUsage;
import com.ftn.security.model.KeyUsage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CreateRootCertificateDto {
    private String adminMail;
    private Date startDate;
    private Date endDate;
    private List<KeyUsage> keyUsages;
    private List<ExtendedKeyUsage> extendedKeyUsages;
}
