package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import model.Person.Person;
import utils.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AccountsController {
    private static final String ACCOUNTS_FILE_PATH = FileHandler.COMPTES_FILE;
    private static ArrayList<Person> people = new ArrayList<>();
   static public String ADMIN_ACCOUNT_TYPE="admin";
    static public String AUDITOR_ACCOUNT_TYPE="auditor";


    public AccountsController() {
        loadAccounts();
        System.out.println("Accounts loaded successfully."+ people);
    }

    // Load accounts from the JSON file
    public void loadAccounts() {
        try {
            List<Person> loadedPeople = FileHandler.loadDataFromJson(ACCOUNTS_FILE_PATH, new TypeReference<List<Person>>() {});
            people = new ArrayList<>(loadedPeople);
            System.out.printf(people.size() + " accounts loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading accounts: " + e.getMessage());
        }
    }

    // Save accounts to the JSON file
    public void saveAccounts() {
        try {
            FileHandler.saveDataInJson(ACCOUNTS_FILE_PATH, people);
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }
    public void createAccount(Person personData) {
        people.add(personData);
        System.out.println("New account added successfully.");

        saveAccounts();
    }


    // Add or edit an account
    public void editAccount(String id, Person updatedPerson) {
        // Search for the existing account with the given ID
        Optional<Person> existingAccount = people.stream()
                .filter(account -> account.getIdAccount().equals(id))
                .findFirst();

        if (existingAccount.isPresent()) {
            int index = people.indexOf(existingAccount.get());

            people.get(index).editAccount(updatedPerson);

            System.out.println("Account updated successfully.");
        } else {
            System.out.println("No account found!");
        }

        saveAccounts(); // Save the updated list of accounts
    }


    public boolean deleteAccount(String idAccount) {
        Optional<Person> accountToDelete = people.stream()
                .filter(account -> account.getIdAccount().equals(idAccount))
                .findFirst();

        if (accountToDelete.isPresent()) {
            people.remove(accountToDelete.get());
            saveAccounts();
            System.out.println("Account deleted successfully.");
            return true;
        } else {
            System.out.println("Account not found.");
            return false;
        }
    }
    // Get an account by email
    public Person getAccount(String email, String password) {
        Optional<Person> account = people.stream()
                .filter(acc -> acc.getEmail().equals(email)
                && acc.getPassword().equals(password))
                .findFirst();
        return account.orElse(null);
    }
    public Person getAccountAdmin(String email, String password) {
        Optional<Person> account = people.stream()
                .filter(acc -> acc.getAccountType().equals(ADMIN_ACCOUNT_TYPE)
                        && acc.getEmail().equals(email)
                        && acc.getPassword().equals(password))
                .findFirst();
        return account.orElse(null);
    }


    public Person getAccountAuditor(String email, String password) {
        Optional<Person> account = people.stream()
                .filter(acc -> acc.getAccountType().equals(AUDITOR_ACCOUNT_TYPE)
                        && acc.getEmail().equals(email)
                        && acc.getPassword().equals(password))
                .findFirst();
        return account.orElse(null);
    }


    // Get all accounts
    public List<Person> getAccounts() {
        return people;
    }
    // Get all accounts
    public List<Person> getAccountsAdmin() {
        return people.stream().filter(acc->acc.getAccountType().equals(ADMIN_ACCOUNT_TYPE)).toList();
    }
    // Get all accounts
    public List<Person> getAccountsAuditor() {
        return people.stream().filter(acc->acc.getAccountType().equals(AUDITOR_ACCOUNT_TYPE)).toList();
    }
    // Get an auditor account by ID
    public Person getAuditorById(String idAuditor) throws Exception {
        Optional<Person> auditorAccount = people.stream()
                .filter(acc -> acc.getAccountType().equals(AUDITOR_ACCOUNT_TYPE)
                        && acc.getIdAccount().equals(idAuditor))
                .findFirst();

        // Throw an exception if the auditor is not found
        return auditorAccount.orElseThrow(() -> new Exception("Auditor not found with ID: " + idAuditor));
    }

}
