package com.ftn.security.repository;

import com.ftn.security.model.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends EntityRepository<Client> {

    @Query("SELECT c FROM Client c WHERE c.mail=?1")
    Client getClientByMail(String mail);

    @Query("SELECT c FROM Client c")
    List<Client> getUsers();
}
