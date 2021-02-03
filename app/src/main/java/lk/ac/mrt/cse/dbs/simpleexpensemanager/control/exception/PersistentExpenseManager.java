package lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.ExpenseManager;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.DatabaseHelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistentAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class PersistentExpenseManager extends ExpenseManager {

    DatabaseHelper database;
    public PersistentExpenseManager(DatabaseHelper database) {
        this.database=database;
        setup();
    }

    @Override
    public void setup() {
        /*** Begin generating dummy data for Persistent Memory implementation ***/

        TransactionDAO PersistentTransactionDAO = new PersistentTransactionDAO(database);
        setTransactionsDAO(PersistentTransactionDAO);

        AccountDAO PersistentAccountDAO = new PersistentAccountDAO(database);
        setAccountsDAO(PersistentAccountDAO);

        // dummy data
        Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        getAccountsDAO().addAccount(dummyAcct1);
        getAccountsDAO().addAccount(dummyAcct2);

        /*** End ***/
    }
}
