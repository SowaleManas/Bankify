## **Bankify - Secure Banking Application** 🚀

**Bankify** is a **secure Java-based banking system** with a **GUI (JavaFX)** and **database-backed account management**. It features **secure authentication, transaction logging, and intrusion detection**.

---

## **🔹 Features**
✅ **User Authentication with Hashed Passwords** (BCrypt)  
✅ **Deposit, Withdraw, and Transfer Money**  
✅ **Transaction History Tracking**  
✅ **Intrusion Detection** (Detect multiple failed logins)  
✅ **Audit Logging** (Log transactions and security events)  
✅ **MySQL Database Integration**  
✅ **JavaFX GUI for User Interaction**  

---

## **🛠️ Technologies Used**
- **Apache Maven** (Project Management & Dependency Handling)
- **Java 23.0.2** (Core Backend)
- **JavaFX** (GUI)
- **MySQL** (Database)
- **BCrypt** (Secure Password Hashing)
- **Maven** (Build & Dependency Management)

---

## **📂 Project Structure**
```
/Bankify
│── src/main/java/bankify/
│   ├── Account.java          # Account handling (Deposit, Withdraw, Transactions)
│   ├── Bank.java             # Manages accounts & transactions
│   ├── Bankify.java          # CLI-based banking system
│   ├── BankifyGUI.java       # JavaFX GUI-based banking system
│   ├── DatabaseManager.java  # Database connection handling
│   ├── SecurityLogger.java   # Security event logging
│   ├── Transaction.java      # Handles transaction objects
│── pom.xml                   # Maven build configuration
│── .env                      # (Ignored) Stores database credentials
│── security_logs.txt         # Stores audit logs of transactions & security events
```

---

## **⚙️ Setup Instructions**
### **1️⃣ Install Java & MySQL**
Ensure you have **Java 23.0.2** and **MySQL 8+** installed.

### **2️⃣ Set Up the Database**
Run the following SQL script in MySQL:
```sql
CREATE DATABASE bankify_db;
USE bankify_db;

CREATE TABLE accounts (
    account_number VARCHAR(20) PRIMARY KEY,
    account_holder VARCHAR(100),
    password_hash VARCHAR(255),
    balance DOUBLE
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20),
    type VARCHAR(10),
    amount DOUBLE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### **3️⃣ Configure Database Credentials**
- Create a `.env` file in the project root:
  ```
  DB_URL=jdbc:mysql://localhost:3306/bankify_db
  DB_USER=root
  DB_PASSWORD=yourpassword
  ```

### **4️⃣ Build the Project**
```sh
mvn clean install
```

### **5️⃣ Run the Application**
For **CLI Version**:
```sh
mvn exec:java -Dexec.mainClass="bankify.Bankify"
```
For **GUI Version**:
```sh
mvn javafx:run
```

---

## **🔐 Security Features**
✅ **Password Hashing:** Uses **BCrypt** for secure password storage.  
✅ **Intrusion Detection:** Detects **multiple failed logins** and logs alerts.  
✅ **Audit Logging:** Logs all **login attempts, transactions, and security events** in `security_logs.txt`.  
✅ **Input Validation:** Prevents **SQL Injection & Invalid Inputs**.

---

## **📜 Audit Logging & Intrusion Detection**
- **All transactions & security events** are logged in `security_logs.txt`
- Example logs:
  ```
  2025-03-05 12:30:01 - Login Success: Account: 123456
  2025-03-05 12:35:10 - Deposit: Account: 123456, Amount: $500
  2025-03-05 12:40:12 - Intrusion Alert: Multiple failed logins detected for Account: 999999
  ```

---

## **📌 Future Improvements**
- 🔹 **Email OTP Verification**
- 🔹 **Two-Factor Authentication (2FA)**
- 🔹 **Admin Dashboard for Transaction Monitoring**
- 🔹 **Enhanced Fraud Detection (Machine Learning)**