package com.zup.api.repository;

import java.util.UUID;

import com.zup.api.entity.Proposal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, UUID> {}
