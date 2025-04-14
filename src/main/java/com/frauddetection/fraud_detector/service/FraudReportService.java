package com.frauddetection.fraud_detector.service;

import com.frauddetection.fraud_detector.model.FraudReport;
import com.frauddetection.fraud_detector.model.Transaction;
import com.frauddetection.fraud_detector.repository.FraudReportRepository;
import com.frauddetection.fraud_detector.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FraudReportService {

    @Autowired
    private FraudReportRepository fraudReportRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public FraudReport createFraudReport(Long transactionId, String reason) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(transactionId);
        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            transaction.setFlaggedAsFraud(true);
            transactionRepository.save(transaction);

            FraudReport report = new FraudReport();
            report.setTransaction(transaction);
            report.setReportedAt(LocalDateTime.now());
            report.setReportReason(reason);

            return fraudReportRepository.save(report);
        }
        return null;
    }

    @Transactional
    public FraudReport resolveFraudReport(Long reportId, String resolution, String resolvedBy) {
        Optional<FraudReport> reportOpt = fraudReportRepository.findById(reportId);
        if (reportOpt.isPresent()) {
            FraudReport report = reportOpt.get();
            report.setResolution(resolution);
            report.setResolvedAt(LocalDateTime.now());
            report.setResolvedBy(resolvedBy);
            return fraudReportRepository.save(report);
        }
        return null;
    }

    public List<FraudReport> getPendingReports() {
       // return fraudReportRepository.findByIsResolvedFalse();
        return null;
    }
}
