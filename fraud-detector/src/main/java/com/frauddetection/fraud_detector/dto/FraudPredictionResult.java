package com.frauddetection.fraud_detector.dto;

import lombok.Data;

@Data
public class FraudPredictionResult {
    private Double fraudProbability;
    private Boolean isFlagged;
    private String[] triggeringFactors;
}