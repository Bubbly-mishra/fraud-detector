package com.frauddetection.fraud_detector.repository;

import com.frauddetection.fraud_detector.model.FraudReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FraudReportRepository extends JpaRepository<FraudReport, Long> {

    List<FraudReport> findByTransactionId(Long transactionId);

    //List<FraudReport> findByIsResolvedFalse();

    List<FraudReport> findByResolvedBy(String resolvedBy);
}
