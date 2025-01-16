package utils.interfaces.objectConverter;

import model.Accounts.Account;
import model.Organization.Organization;

public class AccountConverter implements ObjectConverter<Account> {
    @Override
    public Account convertObject(){
        return new Account();
    }
}
