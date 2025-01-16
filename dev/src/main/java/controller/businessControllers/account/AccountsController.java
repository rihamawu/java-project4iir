package controller.businessControllers.account;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Accounts.Account;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import  utils.JsonFileHandler;


public class AccountsController {
    private static final String ACCOUNTS_FILE_PATH = JsonFileHandler.ACCOUNTS_FILE_PATH;
    private static ArrayList<Account> accounts = new ArrayList<>();
   static public String ADMIN_ACCOUNT_TYPE="admin";
    static public String AUDITOR_ACCOUNT_TYPE="auditor";


    public AccountsController() {
        loadAccounts();
        System.out.println("Accounts loaded successfully."+accounts);
    }

    // Load accounts from the JSON file
    public void loadAccounts() {
        try {
            List<Account> loadedAccounts = JsonFileHandler.loadData(ACCOUNTS_FILE_PATH, new TypeReference<List<Account>>() {});
            accounts = new ArrayList<>(loadedAccounts);
            System.out.printf(accounts.size() + " accounts loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        }
    }

    // Save accounts to the JSON file
    public void saveAccounts() {
        try {
            JsonFileHandler.saveData(ACCOUNTS_FILE_PATH, accounts);
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }
    public void createAccount(Account accountData) {
        accounts.add(accountData);
        System.out.println("New account added successfully.");

        saveAccounts();
    }


    // Add or edit an account
    public void editAccount(String id, Account updatedAccount) {
        // Search for the existing account with the given ID
        Optional<Account> existingAccount = accounts.stream()
                .filter(account -> account.getIdAccount().equals(id))
                .findFirst();

        if (existingAccount.isPresent()) {
            int index = accounts.indexOf(existingAccount.get());

            accounts.get(index).editAccount(updatedAccount);

            System.out.println("Account updated successfully.");
        } else {
            System.out.println("No account found!");
        }

        saveAccounts(); // Save the updated list of accounts
    }


    public boolean deleteAccount(String idAccount) {
        Optional<Account> accountToDelete = accounts.stream()
                .filter(account -> account.getIdAccount().equals(idAccount))
                .findFirst();

        if (accountToDelete.isPresent()) {
            accounts.remove(accountToDelete.get());
            saveAccounts();
            System.out.println("Account deleted successfully.");
            return true;
        } else {
            System.out.println("Account not found.");
            return false;
        }
    }
    // Get an account by email
    public Account getAccount(String email, String password) {
        Optional<Account> account = accounts.stream()
                .filter(acc -> acc.getEmail().equals(email)
                && acc.getPassword().equals(password))
                .findFirst();
        return account.orElse(null);
    }
    public Account getAccountAdmin(String email, String password) {
        Optional<Account> account = accounts.stream()
                .filter(acc -> acc.getAccountType().equals(ADMIN_ACCOUNT_TYPE)
                        && acc.getEmail().equals(email)
                        && acc.getPassword().equals(password))
                .findFirst();
        return account.orElse(null);
    }


    public Account getAccountAuditor(String email, String password) {
        Optional<Account> account = accounts.stream()
                .filter(acc -> acc.getAccountType().equals(AUDITOR_ACCOUNT_TYPE)
                        && acc.getEmail().equals(email)
                        && acc.getPassword().equals(password))
                .findFirst();
        return account.orElse(null);
    }


    // Get all accounts
    public List<Account> getAccounts() {
        return accounts;
    }
    // Get all accounts
    public List<Account> getAccountsAdmin() {
        return accounts.stream().filter(acc->acc.getAccountType().equals(ADMIN_ACCOUNT_TYPE)).toList();
    }
    // Get all accounts
    public List<Account> getAccountsAuditor() {
        return accounts.stream().filter(acc->acc.getAccountType().equals(AUDITOR_ACCOUNT_TYPE)).toList();
    }
    // Get an auditor account by ID
    public Account getAuditorById(String idAuditor) throws Exception {
        Optional<Account> auditorAccount = accounts.stream()
                .filter(acc -> acc.getAccountType().equals(AUDITOR_ACCOUNT_TYPE)
                        && acc.getIdAccount().equals(idAuditor))
                .findFirst();

        // Throw an exception if the auditor is not found
        return auditorAccount.orElseThrow(() -> new Exception("Auditor not found with ID: " + idAuditor));
    }

}
