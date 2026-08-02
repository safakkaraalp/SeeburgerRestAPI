package com.example.restapi.repository;

import com.example.restapi.model.BpInstance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstanceRepository
        extends JpaRepository<BpInstance, String> {
}