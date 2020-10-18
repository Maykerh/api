package com.zup.api.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zup.api.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
