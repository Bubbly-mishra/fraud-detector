package com.frauddetection.fraud_detector.repository;

import com.frauddetection.fraud_detector.model.MLModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MLModelRepository extends JpaRepository<MLModel, Long> {
    MLModel findByIsActiveTrue();
}
