package com.frauddetection.fraud_detector.dto;

import lombok.Data;

@Data
public class FraudResolutionDTO {
    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    private String resolution;
    private String resolvedBy;
}