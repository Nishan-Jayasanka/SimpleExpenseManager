package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

import static java.lang.Long.getLong;

public class PersistentTransactionDAO implements TransactionDAO {

    DatabaseHelper database;

    public PersistentTransactionDAO(DatabaseHelper database) {
        this.database=database;
    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
        database.addTransaction(transaction);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        Cursor cursor = database.getAllTransactions();
        Log.i("MyLogs","Data0");
        ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();
        if( cursor != null) {
            Log.i("MyLogs","Data1");
            while (cursor.moveToNext()) {
                Log.i("MyLogs","Data2");
                Transaction transaction = new Transaction(new Date(cursor.getLong((0))),cursor.getString(2), ExpenseType.valueOf(cursor.getString(3)), cursor.getDouble(4));
                Log.i("MyLogs","Data3");
                transactionsList.add(transaction);
            } ;
        }
        return transactionsList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {

        int size = this.getAllTransactionLogs().size();
        if (size <= limit) {
            return getAllTransactionLogs();
        }
        // return the last <code>limit</code> number of transaction logs
        return getAllTransactionLogs().subList(size - limit, size);
    }
}
