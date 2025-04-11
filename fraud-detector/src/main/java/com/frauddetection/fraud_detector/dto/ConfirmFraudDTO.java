// ConfirmFraudDTO.java
package com.frauddetection.fraud_detector.dto;


import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class ConfirmFraudDTO {
    public Boolean getFraud() {
        return isFraud;
    }

    public void setFraud(Boolean fraud) {
        isFraud = fraud;
    }

    @NotNull
    private Boolean isFraud;
}
