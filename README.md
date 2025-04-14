
# Fraud Detection System – Database Schema

Below are the SQL `CREATE TABLE` statements for all necessary entities in the project. You can execute these in your MySQL database before starting the application (especially if `spring.jpa.hibernate.ddl-auto` is not set to `update`).

---

### 📄 `account` Table

```sql
CREATE TABLE account (
    account_number VARCHAR(50) PRIMARY KEY,
    customer_name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    created_at DATETIME,
    last_login DATETIME,
    typical_transaction_locations VARCHAR(255),
    typical_devices VARCHAR(255),
    typical_transaction_times VARCHAR(255),
    average_transaction_amount DOUBLE
);
```

---

### 📄 `transaction` Table

```sql
CREATE TABLE transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(50),
    amount DOUBLE,
    merchant_name VARCHAR(100),
    merchant_category VARCHAR(100),
    timestamp DATETIME,
    location VARCHAR(100),
    ip_address VARCHAR(45),
    device_id VARCHAR(100),
    average_transaction_amount DOUBLE,
    transaction_frequency INT,
    distance_from_last_transaction DOUBLE,
    is_new_merchant BOOLEAN,
    is_new_location BOOLEAN,
    is_new_device BOOLEAN,
    fraud_probability DOUBLE,
    flagged_as_fraud BOOLEAN,
    confirmed_fraud BOOLEAN,
    CONSTRAINT fk_account FOREIGN KEY (account_number) REFERENCES account(account_number)
);
```

---

### 📄 `fraud_report` Table

```sql
CREATE TABLE fraud_report (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id BIGINT,
    reported_at DATETIME,
    report_reason VARCHAR(255),
    resolution VARCHAR(255),
    resolved_at DATETIME,
    resolved_by VARCHAR(100),
    CONSTRAINT fk_transaction FOREIGN KEY (transaction_id) REFERENCES transaction(id)
);
```

---

### 📄 `ml_model` Table

```sql
CREATE TABLE ml_model (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    model_name VARCHAR(100),
    model_type VARCHAR(100),
    trained_at DATETIME,
    accuracy DOUBLE,
    precision DOUBLE,
    recall DOUBLE,
    f1_score DOUBLE,
    model_path VARCHAR(255),
    is_active BOOLEAN
);
```

---

> ✅ **Note:** Ensure your database (`frauddb` or another name) is created and accessible. You can set it in `application.properties` like:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/frauddb
spring.datasource.username=root
spring.datasource.password=yourpassword
```

# Fraud Detection API - Testing Guide

This guide provides instructions for testing the fraud detection application API.

## Prerequisites

- The application should be up and running
- Local or cloud environment properly configured
- Database connections established

## Testing the API Endpoints

Below are instructions for testing the main functionalities using cURL commands.

### 1. Create a Transaction

```bash
curl -X POST "http://localhost:8080/api/transactions" \
-H "Content-Type: application/json" \
-d '{
  "accountNumber": "ACC1001",
  "amount": 250.0,
  "merchantName": "Etsy",
  "merchantCategory": "Handmade",
  "timestamp": "2025-04-11T04:04:29",
  "location": "New York",
  "ipAddress": "192.168.1.10",
  "deviceId": "device789"
}'
```

### 2. Get All Flagged Transactions

```bash
curl -X GET "http://localhost:8080/api/transactions/flagged"
```

### 3. Confirm Fraud Status for a Transaction

```bash
curl -X PUT "http://localhost:8080/api/transactions/1/confirm-fraud" \
-H "Content-Type: application/json" \
-d '{"fraud": true}'
```

### 4. Create a Fraud Report

```bash
curl -X POST "http://localhost:8080/api/fraud-reports" \
-H "Content-Type: application/json" \
-d '{
  "transactionId": 1,
  "reportReason": "Suspicious transaction behavior"
}'
```

### 5. Get Pending Fraud Reports

```bash
curl -X GET "http://localhost:8080/api/fraud-reports/pending"
```

### 6. Resolve a Fraud Report

```bash
curl -X PUT "http://localhost:8080/api/fraud-reports/1/resolve" \
-H "Content-Type: application/json" \
-d '{
  "resolution": "Fraud confirmed", 
  "resolvedBy": "Admin"
}'
```

### 7. Retrain the ML Model

```bash
curl -X POST "http://localhost:8080/api/ml/retrain"
```

## Verifying API Responses

When testing, verify the following expected behaviors:

| Endpoint | Expected Response |
|----------|------------------|
| Create Transaction | Status `201` with transaction details |
| Get Flagged Transactions | List of transactions marked as fraudulent |
| Confirm Fraud | Status `200` with updated transaction |
| Create Fraud Report | Status `201` with report details |
| Get Pending Reports | List of unresolved fraud reports |
| Resolve Report | Status `200` with updated report |
| Retrain ML | Status `200` with training results |

## Database Verification

After each API call, verify that the appropriate records are being created or updated in your database:

- Transaction table
- Fraud report table
- ML model metadata (if applicable)

## Technical Notes

- The application uses Spring Boot with JPA for data persistence
- Ensure database configurations are correct in your `application.properties` or `application.yml`
- The Fraud Detection service relies on machine learning models, ensure model path configurations are correct

## Troubleshooting

If you encounter issues:
- Check application logs for detailed error messages
- Verify database connection and schema
- Ensure ML model files are accessible to the application
