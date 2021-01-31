package com.martinez.operacionfuegodequasar.controllers;

import com.martinez.operacionfuegodequasar.dtos.request.InformationDTO;
import com.martinez.operacionfuegodequasar.services.topsecretsplit.TopSecretSplitService;
import com.martinez.operacionfuegodequasar.util.SuccessMessage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/topsecret_split")
@RestController
public class TopSecretSplitController {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TopSecretSplitController.class);

    private TopSecretSplitService topSecretSplitService;

    @Autowired
    public void TopSecretSplitController(TopSecretSplitService topSecretSplitService) {
        this.topSecretSplitService = topSecretSplitService;
    }

    @PostMapping(value = "/{satelliteName}")
    public ResponseEntity captureInformation(@PathVariable String satelliteName, @RequestBody InformationDTO informationDTO) {
        LOG.info("TopSecretSplitController.captureInformation -satelliteName: [{}] -distances: [{}] -messages: [{}]", satelliteName, informationDTO.getDistance(), informationDTO.getMessage());
        topSecretSplitService.captureSatelliteInformation(satelliteName, informationDTO);

        return ResponseEntity.ok(new SuccessMessage("Se capturó la información de forma existosa"));
    }

    @GetMapping
    public ResponseEntity getInformation() {
        LOG.info("TopSecretSplitController.getInformation");

        return ResponseEntity.ok(topSecretSplitService.getInformation());
    }

}
