// FraudReportController.java
package com.frauddetection.fraud_detector.controller;


import com.frauddetection.fraud_detector.dto.FraudReportDTO;
import com.frauddetection.fraud_detector.dto.FraudResolutionDTO;
import com.frauddetection.fraud_detector.model.FraudReport;
import com.frauddetection.fraud_detector.service.FraudReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fraud-reports")
public class FraudReportController {

    @Autowired
    private FraudReportService fraudReportService;

    @PostMapping
    public ResponseEntity<FraudReport> createReport(@Valid @RequestBody FraudReportDTO reportDTO) {
        FraudReport report = fraudReportService.createFraudReport(reportDTO.getTransactionId(), reportDTO.getReason());
        return report != null ? ResponseEntity.status(201).body(report) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/pending")
    public ResponseEntity<List<FraudReport>> getPendingReports() {
        return ResponseEntity.ok(fraudReportService.getPendingReports());
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<FraudReport> resolveReport(
            @PathVariable Long id,
            @Valid @RequestBody FraudResolutionDTO resolutionDTO) {
        FraudReport resolved = fraudReportService.resolveFraudReport(id, resolutionDTO.getResolution(), resolutionDTO.getResolvedBy());
        return resolved != null ? ResponseEntity.ok(resolved) : ResponseEntity.notFound().build();
    }
}
