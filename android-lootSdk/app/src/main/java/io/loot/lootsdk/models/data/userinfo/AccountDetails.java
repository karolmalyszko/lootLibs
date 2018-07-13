package io.loot.lootsdk.models.data.userinfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountDetails implements Serializable {

    private ArrayList<Account> accounts;

    public AccountDetails(ArrayList<Account> accounts) {
        this.setAccounts(accounts);
    }

    public AccountDetails(List<Account> accounts) {
        this.setAccounts(new ArrayList<Account>(accounts));
    }

    public AccountDetails() {
        this.accounts = new ArrayList<Account>();
    }

    public AccountDetails(AccountDetails accountDetails) {
        this.accounts = new ArrayList<Account>(accountDetails.getAccounts());
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        if (accounts == null) {
            accounts = new ArrayList<Account>();
        }
        this.accounts = new ArrayList<Account>(accounts);
    }
}

