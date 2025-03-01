package bankify;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BankifyGUI extends Application {
    private Bank bank = new Bank();
    private Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Bankify - Login");

        // Login Form
        Label accLabel = new Label("Enter Account Number:");
        TextField accInput = new TextField();
        Button loginButton = new Button("Login");
        Button createButton = new Button("Create Account");

        loginButton.setOnAction(e -> {
            String accNo = accInput.getText();
            Account account = bank.getAccount(accNo);
            if (account != null) {
                showMainMenu(account);
            } else {
                showAlert("Login Failed", "Account not found.");
            }
        });

        createButton.setOnAction(e -> showCreateAccountScreen());

        VBox layout = new VBox(10, accLabel, accInput, loginButton, createButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 250);

        window.setScene(scene);
        window.show();
    }

    private void showCreateAccountScreen() {
        Stage createWindow = new Stage();
        createWindow.setTitle("Create Account");

        Label accLabel = new Label("Enter Account Number:");
        TextField accInput = new TextField();
        Label nameLabel = new Label("Enter Account Holder Name:");
        TextField nameInput = new TextField();
        Label balanceLabel = new Label("Enter Initial Deposit:");
        TextField balanceInput = new TextField();
        Button createButton = new Button("Create");

        createButton.setOnAction(e -> {
            String accNo = accInput.getText();
            String name = nameInput.getText();
            double balance = Double.parseDouble(balanceInput.getText());

            if (bank.createAccount(accNo, name, balance)) {
                showAlert("Success", "Account created successfully!");
                createWindow.close();
            } else {
                showAlert("Error", "Account creation failed.");
            }
        });

        VBox layout = new VBox(10, accLabel, accInput, nameLabel, nameInput, balanceLabel, balanceInput, createButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 300);

        createWindow.setScene(scene);
        createWindow.show();
    }

    private void showMainMenu(Account account) {
        Stage mainMenu = new Stage();
        mainMenu.setTitle("Bankify - Main Menu");

        Label welcomeLabel = new Label("Welcome, " + account.getAccountName());
        Label balanceLabel = new Label("Balance: $" + account.getBalance());


        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");
        Button transferButton = new Button("Transfer");
        Button refreshButton = new Button("Refresh Balance");
        Button logoutButton = new Button("Log Out");

        refreshButton.setOnAction(e -> {
            Account updatedAccount = bank.getAccount(account.getAccountNumber());
            balanceLabel.setText("Balance: $" + updatedAccount.getBalance());
        });

        depositButton.setOnAction(e -> {
            showDepositScreen(account);
            refreshBalance(balanceLabel, account);
        });
        withdrawButton.setOnAction(e -> {
            showWithdrawScreen(account);
            refreshBalance(balanceLabel, account);
        });

        transferButton.setOnAction(e -> {
            showTransferScreen(account);
            refreshBalance(balanceLabel, account);
        });

        logoutButton.setOnAction(e -> mainMenu.close());

        VBox layout = new VBox(10, welcomeLabel, depositButton, withdrawButton, transferButton, balanceButton, logoutButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 350, 350);

        mainMenu.setScene(scene);
        mainMenu.show();
    }

    private void showDepositScreen(Account account) {
        Stage depositWindow = new Stage();
        depositWindow.setTitle("Deposit");

        Label amountLabel = new Label("Enter Deposit Amount:");
        TextField amountInput = new TextField();
        Button depositButton = new Button("Deposit");

        depositButton.setOnAction(e -> {
            double amount = Double.parseDouble(amountInput.getText());
            if (account.deposit(amount)) {
                showAlert("Success", "Deposit successful.");
                depositWindow.close();
            } else {
                showAlert("Error", "Deposit failed.");
            }
        });

        VBox layout = new VBox(10, amountLabel, amountInput, depositButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 200);

        depositWindow.setScene(scene);
        depositWindow.show();
    }

    private void refreshBalance(Label balanceLabel, Account account) {
        Account updatedAccount = bank.getAccount(account.getAccountNumber());
        balanceLabel.setText("Balance: $" + updatedAccount.getBalance());
    }

    private void showWithdrawScreen(Account account) {
        Stage withdrawWindow = new Stage();
        withdrawWindow.setTitle("Withdraw");

        Label amountLabel = new Label("Enter Withdrawal Amount:");
        TextField amountInput = new TextField();
        Button withdrawButton = new Button("Withdraw");

        withdrawButton.setOnAction(e -> {
            double amount = Double.parseDouble(amountInput.getText());
            if (account.withdraw(amount)) {
                showAlert("Success", "Withdrawal successful.");
                withdrawWindow.close();
            } else {
                showAlert("Error", "Insufficient funds.");
            }
        });

        VBox layout = new VBox(10, amountLabel, amountInput, withdrawButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 200);

        withdrawWindow.setScene(scene);
        withdrawWindow.show();
    }

    private void showTransferScreen(Account account) {
        Stage transferWindow = new Stage();
        transferWindow.setTitle("Transfer");

        Label accLabel = new Label("Recipient Account:");
        TextField accInput = new TextField();
        Label amountLabel = new Label("Enter Amount:");
        TextField amountInput = new TextField();
        Button transferButton = new Button("Transfer");

        transferButton.setOnAction(e -> {
            String toAcc = accInput.getText();
            double amount = Double.parseDouble(amountInput.getText());

            if (bank.transfer(account.getAccountNumber(), toAcc, amount)) {
                showAlert("Success", "Transfer successful.");
                transferWindow.close();
            } else {
                showAlert("Error", "Transfer failed.");
            }
        });

        VBox layout = new VBox(10, accLabel, accInput, amountLabel, amountInput, transferButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 300, 250);

        transferWindow.setScene(scene);
        transferWindow.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
