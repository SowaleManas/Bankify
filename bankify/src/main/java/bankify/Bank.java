package bankify;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class Bank {
    public boolean createAccount(String accNo, String accHolder, String password, double balance) {
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) return false;

        // Hashing the password before storing it
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

        try {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO accounts (account_number, account_holder, password_hash, balance) VALUES (?, ?, ?, ?)"
            );
            stmt.setString(1, accNo);
            stmt.setString(2, accHolder);
            stmt.setString(3, hashedPassword);
            stmt.setDouble(4, balance);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("❌ Account creation failed: " + e.getMessage());
        }
        return false;
    }

    public Account getAccount(String accountNumber) {
        Connection conn = DatabaseManager.getConnection();
        if (conn == null) return null;

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM accounts WHERE account_number = ?");
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Account(
                    rs.getString("account_number"),
                    rs.getString("account_holder"),
                    rs.getString("password_hash"),  // Get hashed password
                    rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Error fetching account: " + e.getMessage());
        }
        return null;
    }

    public boolean transfer(String fromAcc, String toAcc, double amount) {
        Account sender = getAccount(fromAcc);
        Account receiver = getAccount(toAcc);

        if (sender == null || receiver == null) {
            System.out.println("❌ Transfer failed: One or both accounts do not exist.");
            return false;
        }

        if (!sender.withdraw(amount)) {
            System.out.println("❌ Transfer failed: Insufficient funds.");
            return false;
        }

        receiver.deposit(amount);
        System.out.println("✅ Transfer successful: $" + amount + " sent to " + toAcc);
        SecurityLogger.logEvent("Transfer", "Sender: " + sender.getAccountNumber() + ", Reciever: " + receiver.getAccountNumber() + ", Amount: $" + amount);
        return true;
    }
}
