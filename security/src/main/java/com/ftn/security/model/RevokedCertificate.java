package com.ftn.security.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
public class RevokedCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String certificateSerialNumber;

    private Date revocationTime;

    @Enumerated(EnumType.STRING)
    private CertificateRevocationReason certificateRevocationReason;
}
