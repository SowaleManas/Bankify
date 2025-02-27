package bankify;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public Account(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean saveAccount() {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO accounts VALUES (?, ?, ?)")) {
            stmt.setString(1, accountNumber);
            stmt.setString(2, accountHolder);
            stmt.setDouble(3, balance);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Failed to create account: " + e.getMessage());
            return false;
        }
    }

    public static Account getAccount(String accountNumber) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM accounts WHERE account_number = ?")) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Account(rs.getString("account_number"), rs.getString("account_holder"), rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error retrieving account: " + e.getMessage());
        }
        return null;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Deposit amount must be greater than zero.");
            return false;
        }
        balance += amount;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE account_number = ?")) {
            stmt.setDouble(1, balance);
            stmt.setString(2, accountNumber);
            stmt.executeUpdate();
            saveTransaction("Deposit", amount);
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Deposit failed: " + e.getMessage());
            return false;
        }
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("❌ Withdrawal amount must be greater than zero.");
            return false;
        }
        if (amount > balance) {
            System.out.println("❌ Withdrawal failed: Insufficient balance.");
            return false;
        }
        balance -= amount;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE account_number = ?")) {
            stmt.setDouble(1, balance);
            stmt.setString(2, accountNumber);
            stmt.executeUpdate();
            saveTransaction("Withdrawal", amount);
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Withdrawal failed: " + e.getMessage());
            return false;
        }
    }

    private void saveTransaction(String type, double amount) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO transactions (account_number, type, amount) VALUES (?, ?, ?)")) {
            stmt.setString(1, accountNumber);
            stmt.setString(2, type);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("❌ Failed to save transaction: " + e.getMessage());
        }
    }

    public void viewBalance() {
        System.out.println("💰 Current Balance: $" + balance);
    }

    public void viewTransactionHistory() {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM transactions WHERE account_number = ?")) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            System.out.println("\n📜 Transaction History:");
            while (rs.next()) {
                System.out.println(rs.getString("timestamp") + " - " + rs.getString("type") + ": $" + rs.getDouble("amount"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Failed to retrieve transaction history: " + e.getMessage());
        }
    }
}
