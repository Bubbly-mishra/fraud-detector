package com.frauddetection.fraud_detector.controller;

import com.frauddetection.fraud_detector.service.FraudDetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ml")
public class MLController {

    @Autowired
    private FraudDetectionService fraudDetectionService;

    @PostMapping("/retrain")
    public ResponseEntity<String> retrainModel() {
        fraudDetectionService.retrainModel();
        return new ResponseEntity<>("Model retraining initiated", HttpStatus.OK);
    }
}
