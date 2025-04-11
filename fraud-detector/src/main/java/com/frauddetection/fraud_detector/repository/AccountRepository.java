package com.frauddetection.fraud_detector.repository;


import com.frauddetection.fraud_detector.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
}
