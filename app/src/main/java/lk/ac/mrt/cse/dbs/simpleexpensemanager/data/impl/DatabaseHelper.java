package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "180269F.db";
    public static final String TABLE_1 = "Account_Table";
    public static final String T1_COL_1 = "Account_Number";
    public static final String T1_COL_2 = "Account_Holder";
    public static final String T1_COL_3 = "Bank";
    public static final String T1_COL_4= "Balance";
    public static final String TABLE_2 = "Transaction_Table";
    public static final String T2_COL_1 = "Date";
    public static final String T2_COL_2 = "Account_Number";
    public static final String T2_COL_3 = "Expense_Type";
    public static final String T2_COL_4= "Amount";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Account_Table (Account_Number String PRIMARY KEY, Account_Holder TEXT, Bank TEXT, Balance DOUBLE);");
        db.execSQL("create table Transaction_Table (Date TEXT,Transaction_ID INTEGER PRIMARY KEY AUTOINCREMENT,Account_Number String, Expense_Type TEXT, Amount DOUBLE,FOREIGN KEY(Account_Number) REFERENCES Account_Table);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_2);
        onCreate(db);
    }

    public boolean addAccount(Account account){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T1_COL_1, account.getAccountNo());
        contentValues.put(T1_COL_2, account.getAccountHolderName());
        contentValues.put(T1_COL_3, account.getBankName());
        contentValues.put(T1_COL_4, account.getBalance());
        long result = db.insert(TABLE_1,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean updateAccount(String Account_Number,String Account_Holder, String Bank,Double Balance){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T1_COL_1, Account_Number);
        contentValues.put(T1_COL_2, Account_Holder);
        contentValues.put(T1_COL_3, Bank);
        contentValues.put(T1_COL_4, Balance);
        db.update(TABLE_1, contentValues, "Account_Number=?",new String []{Account_Number});
        return true;
    }

    public Integer removeAccount(String Account_Number){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_1, "Account_Number=?", new String []{Account_Number});
    }

    public Cursor getAllAccounts(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM Account_Table", null);
        return (result);
    }

    public boolean addTransaction(Transaction transaction){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(T2_COL_1, transaction.getDate().getTime());
        contentValues.put(T2_COL_2, transaction.getAccountNo());
        contentValues.put(T2_COL_3, transaction.getExpenseType().toString());
        contentValues.put(T2_COL_4, transaction.getAmount());
        long result = db.insert(TABLE_2,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public Cursor getAllTransactions(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM Transaction_Table", null);
        return (result);
    }
}
