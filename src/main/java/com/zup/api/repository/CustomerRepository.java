package com.zup.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.zup.api.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByCpf(String cpf);
}
