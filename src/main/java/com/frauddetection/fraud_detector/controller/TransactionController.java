// TransactionController.java
package com.frauddetection.fraud_detector.controller;

import com.frauddetection.fraud_detector.dto.ConfirmFraudDTO;
import com.frauddetection.fraud_detector.dto.TransactionDTO;
import com.frauddetection.fraud_detector.model.Account;
import com.frauddetection.fraud_detector.model.Transaction;
import com.frauddetection.fraud_detector.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        // You'd convert DTO to entity here (add a mapper method or use ModelMapper)
        Transaction transaction = mapToEntity(transactionDTO);
        Transaction processed = transactionService.processTransaction(transaction);
        return ResponseEntity.status(201).body(processed);
    }

    @GetMapping("/flagged")
    public ResponseEntity<List<Transaction>> getFlaggedTransactions() {
        return ResponseEntity.ok(transactionService.getFlaggedTransactions());
    }

    @PutMapping("/{id}/confirm-fraud")
    public ResponseEntity<Transaction> confirmFraudStatus(
            @PathVariable Long id,
            @Valid @RequestBody ConfirmFraudDTO dto) {
        Transaction updated = transactionService.confirmFraudStatus(id, dto.getFraud());
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    private Transaction mapToEntity(TransactionDTO dto) {
        Transaction t = new Transaction();
        Account account = new Account();
        account.setAccountNumber(dto.getAccountNumber());
        t.setAccount(account);
        t.setAmount(dto.getAmount());
        t.setMerchantName(dto.getMerchantName());
        t.setMerchantCategory(dto.getMerchantCategory());
        t.setTimestamp(dto.getTimestamp());
        t.setLocation(dto.getLocation());
        t.setIpAddress(dto.getIpAddress());
        t.setDeviceId(dto.getDeviceId());
        return t;
    }
}
