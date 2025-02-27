package bankify;

import java.util.Scanner;

public class Bankify {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        Account loggedInAccount = null;

        while (true) {
            System.out.println("\n==== Welcome to Bankify ====");
            System.out.println("1. Create Account");
            System.out.println("2. Log In");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                System.out.print("Enter account number: ");
                String accNo = scanner.nextLine();
                System.out.print("Enter account holder name: ");
                String holderName = scanner.nextLine();
                System.out.print("Enter initial deposit: ");
                double initialDeposit = scanner.nextDouble();
                if (bank.createAccount(accNo, holderName, initialDeposit)) {
                    System.out.println("✅ Account created successfully!");
                } else {
                    System.out.println("❌ Account creation failed.");
                }
            } else if (choice == 2) {
                System.out.print("Enter account number: ");
                String accNo = scanner.nextLine();
                loggedInAccount = bank.getAccount(accNo);
                if (loggedInAccount != null) {
                    System.out.println("✅ Login successful!");
                    userMenu(scanner, loggedInAccount, bank);
                } else {
                    System.out.println("❌ Login failed: Account not found.");
                }
            } else if (choice == 3) {
                System.out.println("Exiting Bankify...");
                scanner.close();
                return;
            } else {
                System.out.println("❌ Invalid option! Try again.");
            }
        }
    }

    private static void userMenu(Scanner scanner, Account account, Bank bank) {
        while (true) {
            System.out.println("\n==== User Menu ====");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Transaction History");
            System.out.println("5. View Balance");
            System.out.println("6. Log Out");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter deposit amount: ");
                double amount = scanner.nextDouble();
                account.deposit(amount);
            } else if (choice == 2) {
                System.out.print("Enter withdrawal amount: ");
                double amount = scanner.nextDouble();
                account.withdraw(amount);
            } else if (choice == 3) {
                System.out.print("Enter recipient account number: ");
                String toAcc = scanner.nextLine();
                System.out.print("Enter transfer amount: ");
                double amount = scanner.nextDouble();
                bank.transfer(account.getAccountNumber(), toAcc, amount);            
            } else if (choice == 4) {
                account.viewTransactionHistory();
            } else if (choice == 5) {
                account.viewBalance();
            } else if (choice == 6) {
                System.out.println("✅ Logged out.");
                return;
            } else {
                System.out.println("❌ Invalid option! Try again.");
            }
        }
    }
}
