package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistentAccountDAO implements AccountDAO {

    DatabaseHelper database;

    public PersistentAccountDAO(DatabaseHelper database) {
        this.database=database;
    }

    @Override
    public List<String> getAccountNumbersList() {
        Cursor cursor=database.getAllAccounts();
        ArrayList<String> accountNumbers = new ArrayList<String>();
        if( cursor != null && cursor.moveToFirst() ) {
            do {
                String accountNumber = cursor.getString(0);
                accountNumbers.add(accountNumber);
            } while (cursor.moveToNext());
        }
        return accountNumbers;
    }

    @Override
    public List<Account> getAccountsList() {
        Cursor cursor=database.getAllAccounts();
        if (cursor != null) {
            cursor.moveToFirst();
        }
        ArrayList<Account> accountsList = null;
        do {
            Account account = new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3));
            accountsList.add(account);
        } while (cursor.moveToNext());
        return accountsList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Cursor cursor=database.getAllAccounts();
        Account account = null;
        if (cursor != null) {
            while (cursor.moveToNext()){
                if (cursor.getString(0).equals(accountNo)) {
                    account = new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getDouble(3));
                }
            }
        }
        return account;
    }

    @Override
    public void addAccount(Account account) {
        database.addAccount(account);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        database.removeAccount(accountNo);
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        if (! this.getAccountNumbersList().contains(accountNo)) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
        Account account = this.getAccount(accountNo);
        // specific implementation based on the transaction type
        switch (expenseType) {
            case EXPENSE:
                account = this.getAccount(accountNo);
                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                account = this.getAccount(accountNo);
                account.setBalance(account.getBalance() + amount);
                break;
        }
        database.updateAccount(accountNo,account.getAccountHolderName(),account.getBankName(),account.getBalance());
    }
}
