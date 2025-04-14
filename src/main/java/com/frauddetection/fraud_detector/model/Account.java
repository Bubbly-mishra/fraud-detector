package com.frauddetection.fraud_detector.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Account {
    @Id
    private String accountNumber;

    private String customerName;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Transaction> transactions;

    // Behavioral patterns
    private String typicalTransactionLocations;
    private String typicalDevices;
    private String typicalTransactionTimes;
    private Double averageTransactionAmount;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getTypicalTransactionLocations() {
        return typicalTransactionLocations;
    }

    public void setTypicalTransactionLocations(String typicalTransactionLocations) {
        this.typicalTransactionLocations = typicalTransactionLocations;
    }

    public String getTypicalDevices() {
        return typicalDevices;
    }

    public void setTypicalDevices(String typicalDevices) {
        this.typicalDevices = typicalDevices;
    }

    public String getTypicalTransactionTimes() {
        return typicalTransactionTimes;
    }

    public void setTypicalTransactionTimes(String typicalTransactionTimes) {
        this.typicalTransactionTimes = typicalTransactionTimes;
    }

    public Double getAverageTransactionAmount() {
        return averageTransactionAmount;
    }

    public void setAverageTransactionAmount(Double averageTransactionAmount) {
        this.averageTransactionAmount = averageTransactionAmount;
    }
}
