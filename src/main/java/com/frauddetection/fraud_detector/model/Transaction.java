package com.frauddetection.fraud_detector.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_number", nullable = false)
    private Account account;

    private Double amount;
    private String merchantName;
    private String merchantCategory;
    private LocalDateTime timestamp;
    private String location;
    private String ipAddress;
    private String deviceId;

    // Derived features
    private Double averageTransactionAmount;
    private Integer transactionFrequency;
    private Double distanceFromLastTransaction;
    private Boolean isNewMerchant;
    private Boolean isNewLocation;
    private Boolean isNewDevice;

    // Fraud determination
    private Double fraudProbability;
    private Boolean flaggedAsFraud;
    private Boolean confirmedFraud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCategory() {
        return merchantCategory;
    }

    public void setMerchantCategory(String merchantCategory) {
        this.merchantCategory = merchantCategory;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Double getAverageTransactionAmount() {
        return averageTransactionAmount;
    }

    public void setAverageTransactionAmount(Double averageTransactionAmount) {
        this.averageTransactionAmount = averageTransactionAmount;
    }

    public Integer getTransactionFrequency() {
        return transactionFrequency;
    }

    public void setTransactionFrequency(Integer transactionFrequency) {
        this.transactionFrequency = transactionFrequency;
    }

    public Double getDistanceFromLastTransaction() {
        return distanceFromLastTransaction;
    }

    public void setDistanceFromLastTransaction(Double distanceFromLastTransaction) {
        this.distanceFromLastTransaction = distanceFromLastTransaction;
    }

    public Boolean getNewMerchant() {
        return isNewMerchant;
    }

    public void setNewMerchant(Boolean newMerchant) {
        isNewMerchant = newMerchant;
    }

    public Boolean getNewLocation() {
        return isNewLocation;
    }

    public void setNewLocation(Boolean newLocation) {
        isNewLocation = newLocation;
    }

    public Boolean getNewDevice() {
        return isNewDevice;
    }

    public void setNewDevice(Boolean newDevice) {
        isNewDevice = newDevice;
    }

    public Double getFraudProbability() {
        return fraudProbability;
    }

    public void setFraudProbability(Double fraudProbability) {
        this.fraudProbability = fraudProbability;
    }

    public Boolean getFlaggedAsFraud() {
        return flaggedAsFraud;
    }

    public void setFlaggedAsFraud(Boolean flaggedAsFraud) {
        this.flaggedAsFraud = flaggedAsFraud;
    }

    public Boolean getConfirmedFraud() {
        return confirmedFraud;
    }

    public void setConfirmedFraud(Boolean confirmedFraud) {
        this.confirmedFraud = confirmedFraud;
    }
}
