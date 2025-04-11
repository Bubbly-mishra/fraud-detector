package com.frauddetection.fraud_detector.service;


import com.frauddetection.fraud_detector.model.Account;
import com.frauddetection.fraud_detector.model.Transaction;
import com.frauddetection.fraud_detector.repository.AccountRepository;
import com.frauddetection.fraud_detector.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FraudDetectionService fraudDetectionService;

    @Transactional
    public Transaction processTransaction(Transaction transaction) {
        if (transaction.getTimestamp() == null) {
            transaction.setTimestamp(LocalDateTime.now());
        }

        enrichTransactionWithFeatures(transaction);
        Double fraudProbability = fraudDetectionService.predictFraud(transaction);
        transaction.setFraudProbability(fraudProbability);
        transaction.setFlaggedAsFraud(fraudProbability > 0.7);

        return transactionRepository.save(transaction);
    }

    private void enrichTransactionWithFeatures(Transaction transaction) {
        String accountNumber = transaction.getAccount().getAccountNumber();
        Optional<Account> accountOpt = accountRepository.findById(accountNumber);

        if (accountOpt.isPresent()) {
            List<Transaction> recentTransactions = transactionRepository.findRecentTransactions(accountNumber);

            if (!recentTransactions.isEmpty()) {
                double avgAmount = recentTransactions.stream()
                        .mapToDouble(Transaction::getAmount)
                        .average()
                        .orElse(0.0);
                transaction.setAverageTransactionAmount(avgAmount);

                LocalDateTime oneWeekAgo = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
                List<Transaction> weekTransactions = transactionRepository
                        .findByAccountAccountNumberAndTimestampBetween(accountNumber, oneWeekAgo, LocalDateTime.now());
                transaction.setTransactionFrequency(weekTransactions.size() / 7);

                transaction.setNewMerchant(recentTransactions.stream()
                        .noneMatch(t -> t.getMerchantName().equals(transaction.getMerchantName())));
                transaction.setNewLocation(recentTransactions.stream()
                        .noneMatch(t -> t.getLocation().equals(transaction.getLocation())));
                transaction.setNewDevice(recentTransactions.stream()
                        .noneMatch(t -> t.getDeviceId().equals(transaction.getDeviceId())));

                if (!recentTransactions.isEmpty() &&
                        !transaction.getLocation().equals(recentTransactions.get(0).getLocation())) {
                    transaction.setDistanceFromLastTransaction(100.0);
                } else {
                    transaction.setDistanceFromLastTransaction(0.0);
                }

            } else {
                transaction.setAverageTransactionAmount(transaction.getAmount());
                transaction.setTransactionFrequency(1);
                transaction.setNewMerchant(true);
                transaction.setNewLocation(true);
                transaction.setNewDevice(true);
                transaction.setDistanceFromLastTransaction(0.0);
            }
        }
    }

    public List<Transaction> getFlaggedTransactions() {
        return transactionRepository.findAllFlaggedUnconfirmed();
    }

    @Transactional
    public Transaction confirmFraudStatus(Long transactionId, boolean isFraud) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(transactionId);
        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            transaction.setConfirmedFraud(isFraud);
            fraudDetectionService.updateModelWithFeedback(transaction);
            return transactionRepository.save(transaction);
        }
        return null;
    }
}
