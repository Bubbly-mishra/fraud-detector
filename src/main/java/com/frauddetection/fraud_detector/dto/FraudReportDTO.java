package com.frauddetection.fraud_detector.dto;

import lombok.Data;

@Data
public class FraudReportDTO {
    private Long transactionId;
    private String reason;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}