package com.zup.api.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import com.zup.api.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {}
