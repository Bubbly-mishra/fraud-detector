
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
