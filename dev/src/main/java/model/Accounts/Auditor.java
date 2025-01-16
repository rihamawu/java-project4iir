package model.Accounts;

public class Auditor extends Account {


    public Auditor(String firstName, String lastName, String email, String password, String phoneNumber, String domain, String accountType) {
        super(firstName, lastName, email, password, phoneNumber, domain, accountType);

    }

    @Override
    public String toString() {
        return "Auditor{"
                + super.getString() +
                '}';
    }
}