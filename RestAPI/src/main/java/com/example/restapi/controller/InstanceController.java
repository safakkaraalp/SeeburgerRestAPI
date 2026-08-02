package com.example.restapi.controller;

import com.example.restapi.model.BpInstance;
import com.example.restapi.service.InstanceService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/instances")
public class InstanceController {

    private final InstanceService instanceService;

    /**
     * Übergibt den Service an den Controller.
     */
    public InstanceController(
            InstanceService instanceService
    ) {
        this.instanceService =
                instanceService;
    }

    /**
     * Gibt alle Instanzen mit info11 und system zurück.
     */
    @GetMapping
    public ResponseEntity<List<BpInstance>> getAllInstances() {

        List<BpInstance> instanzen =
                instanceService.getAllInstances();

        return ResponseEntity.ok(instanzen);
    }
}