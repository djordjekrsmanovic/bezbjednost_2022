package com.ftn.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LoadCertificateForSigningDto {

    Date dateFrom;
    Date dateTo;
}
