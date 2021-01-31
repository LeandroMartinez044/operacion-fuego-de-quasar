package com.martinez.operacionfuegodequasar.controllers;

import com.martinez.operacionfuegodequasar.dtos.request.SatelliteRequestDTO;
import com.martinez.operacionfuegodequasar.services.topsecret.TopSecretService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/topsecret")
@RestController
public class TopSecretController {

    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TopSecretController.class);

    private TopSecretService topSecretService;

    @Autowired
    public void TopSecretController(TopSecretService topSecretService){
        this.topSecretService = topSecretService;
    }

    @PostMapping
    public ResponseEntity getInformation(@RequestBody SatelliteRequestDTO satelliteRequestDTO){
        LOG.info("TopSecretController.getInformation");
        return ResponseEntity.ok(topSecretService.processInformationFromSatellites(satelliteRequestDTO));
    }
}
