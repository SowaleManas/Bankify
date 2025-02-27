package bankify;

public class Bank {
    public boolean createAccount(String accountNumber, String holderName, double initialBalance) {
        Account account = new Account(accountNumber, holderName, initialBalance);
        return account.saveAccount();
    }

    public Account getAccount(String accountNumber) {
        return Account.getAccount(accountNumber);
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
