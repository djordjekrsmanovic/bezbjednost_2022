package com.ftn.security.repository;

import com.ftn.security.model.RevokedCertificate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RevokeCertificateRepository extends EntityRepository<RevokedCertificate> {

    @Query("SELECT c FROM RevokedCertificate c WHERE c.certificateSerialNumber=?1")
    RevokedCertificate getCertificateBySerialNumber(String serialNumber);
}
