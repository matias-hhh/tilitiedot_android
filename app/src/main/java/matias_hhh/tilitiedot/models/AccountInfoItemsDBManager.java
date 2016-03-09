package matias_hhh.tilitiedot.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class maintains the database connection and supports adding, deleting and
 * listing AccountInfoItems turning them into rows in account_info_item-table in SQLite-db.
 * DB is accessed through AccountInfoItemsDBHelper-class
 */
public class AccountInfoItemsDBManager {

    private SQLiteDatabase db;
    private AccountInfoItemsDBHelper dbHelper;

    // Database fields
    private String[] allColumns = {AccountInfoItemsDBHelper.ID, AccountInfoItemsDBHelper.OWNER,
            AccountInfoItemsDBHelper.ACCOUNT_NUMBER, AccountInfoItemsDBHelper.BIC_CODE};

    public AccountInfoItemsDBManager(Context context) {
        dbHelper = new AccountInfoItemsDBHelper(context);
    }

    // Try to open a db connection
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    // Close the db connection
    public void close() {
        dbHelper.close();
    }

    // Create a row into the account_info_item-table
    public AccountInfoItem createAccountInfoItem(String owner, String accountNumber) {

        // Create ContentValues-object from the values
        ContentValues values = new ContentValues();
        values.put(AccountInfoItemsDBHelper.OWNER, owner);
        values.put(AccountInfoItemsDBHelper.ACCOUNT_NUMBER, accountNumber);

       return insertContentValuesIntoDB(values);
    }

    public AccountInfoItem createAccountInfoItem(String owner, String accountNumber,
                                                 String bicCode) {

        // Create ContentValues-object from the values
        ContentValues values = new ContentValues();
        values.put(AccountInfoItemsDBHelper.OWNER, owner);
        values.put(AccountInfoItemsDBHelper.ACCOUNT_NUMBER, accountNumber);
        values.put(AccountInfoItemsDBHelper.BIC_CODE, bicCode);

        return insertContentValuesIntoDB(values);
    }

    public void deleteAccountInfoItem(AccountInfoItem accountInfoItem) {
        long id = accountInfoItem.getId();
        System.out.println("Row from account_info_item deleted with id: " + id);
        db.delete(AccountInfoItemsDBHelper.TABLE_NAME, AccountInfoItemsDBHelper.ID + " = " + id,
                null);
    }

    public List<AccountInfoItem> getAllAccountInfoItems() {
        List<AccountInfoItem> accountInfoItems = new ArrayList<>();

        // Get all rows from account_info-table
        Cursor cursor = db.query(AccountInfoItemsDBHelper.TABLE_NAME, allColumns, null, null, null,
                null, null);

        // Iterate through the results converting them to AccountInfoItem-
        // objects and adding them to AccountInfoItems-list
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AccountInfoItem accountInfoItem = cursorToAccountInfoItem(cursor);
            accountInfoItems.add(accountInfoItem);
            cursor.moveToNext();
        }

        return accountInfoItems;
    }

    // Build an AccountInfoItem-object from the row the cursor
    // is pointing to from all not-null-columns (bic_code is optional)
    private AccountInfoItem cursorToAccountInfoItem(Cursor cursor) {

        if (cursor.isNull(3)) {
            return new AccountInfoItem(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
        } else {
            return new AccountInfoItem(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3));
        }
    }

    private AccountInfoItem insertContentValuesIntoDB(ContentValues values) {

        // Insert the values into a row in account_info_item-table.
        long insertId = db.insert(AccountInfoItemsDBHelper.TABLE_NAME, null, values);

        // Get the newly created row and make an AccountInfoItem object from it.
        Cursor cursor = db.query(AccountInfoItemsDBHelper.TABLE_NAME, allColumns,
                AccountInfoItemsDBHelper.ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        AccountInfoItem newAccountInfoItem = cursorToAccountInfoItem(cursor);
        cursor.close();

        return newAccountInfoItem;
    }
}
