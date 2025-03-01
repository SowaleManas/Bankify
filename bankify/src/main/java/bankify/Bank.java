package bankify;

public class Bank {
    public boolean createAccount(String accountNumber, String holderName, double initialBalance) {
        Account account = new Account(accountNumber, holderName, initialBalance);
        return account.saveAccount();
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
        return true;
    }
}
