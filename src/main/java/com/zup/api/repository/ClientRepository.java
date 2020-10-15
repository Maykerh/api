package com.zup.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.zup.api.entity.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);

    Optional<Client> findByCpf(String cpf);
}
