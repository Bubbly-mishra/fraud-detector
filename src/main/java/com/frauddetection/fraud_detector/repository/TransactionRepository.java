package com.frauddetection.fraud_detector.repository;

import com.frauddetection.fraud_detector.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccount_AccountNumber(String accountNumber);

    List<Transaction> findByAccountAccountNumberAndTimestampBetween(
        String accountNumber, LocalDateTime start, LocalDateTime end);

    @Query("SELECT t FROM Transaction t WHERE t.flaggedAsFraud = true AND t.confirmedFraud IS NULL")
    List<Transaction> findAllFlaggedUnconfirmed();

    @Query("SELECT t FROM Transaction t WHERE t.account.accountNumber = :accountNumber ORDER BY t.timestamp DESC")
    List<Transaction> findRecentTransactions(String accountNumber);


}
