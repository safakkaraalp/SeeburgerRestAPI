package com.example.restapi.service;

import com.example.restapi.model.BpInstance;
import com.example.restapi.repository.InstanceRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstanceService {

    private final InstanceRepository instanceRepository;

    /**
     * Übergibt das Repository an den Service.
     */
    public InstanceService(
            InstanceRepository instanceRepository
    ) {
        this.instanceRepository =
                instanceRepository;
    }

    /**
     * Liest alle Prozessinstanzen aus der Datenbank.
     */
    public List<BpInstance> getAllInstances() {

        return instanceRepository.findAll();
    }
}