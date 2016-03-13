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

    /**
     * Try to open a db-connection. Throws SQLException if it fails.
     */
    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    /**
     * Close the db-connection.
     */
    public void close() {
        dbHelper.close();
    }

    /**
    * Create a row into the account_info_item-table, no bicCode.
     *
     * @param owner: value for owner-field
     * @param accountNumber: value for account_number-field
     * @return AccountInfoItem-object from the created row.
    */
    public AccountInfoItem createAccountInfoItem(String owner, String accountNumber) {

        // Create ContentValues-object from the values
        ContentValues values = new ContentValues();
        values.put(AccountInfoItemsDBHelper.OWNER, owner);
        values.put(AccountInfoItemsDBHelper.ACCOUNT_NUMBER, accountNumber);

       return insertContentValuesIntoDB(values);
    }

    /**
     * Create a row into the account_info_item-table with bicCode.
     *
     * @param owner: value for owner-field
     * @param accountNumber: value for account_number-field
     * @param bicCode: value for bic_code-field
     * @return AccountInfoItem-object from the created row.
     */
    public AccountInfoItem createAccountInfoItem(String owner, String accountNumber,
                                                 String bicCode) {

        ContentValues values = new ContentValues();
        values.put(AccountInfoItemsDBHelper.OWNER, owner);
        values.put(AccountInfoItemsDBHelper.ACCOUNT_NUMBER, accountNumber);
        values.put(AccountInfoItemsDBHelper.BIC_CODE, bicCode);

        return insertContentValuesIntoDB(values);
    }

    /**
     * Update a given AccountInfoItem in the db.
     *
     * @param accountInfoItem: The item to be updated.
     */
    public void updateAccountInfoItem(AccountInfoItem accountInfoItem) {
        ContentValues values = new ContentValues();
        values.put(AccountInfoItemsDBHelper.OWNER, accountInfoItem.getOwner());
        values.put(AccountInfoItemsDBHelper.ACCOUNT_NUMBER, accountInfoItem.getAccountNumber());
        values.put(AccountInfoItemsDBHelper.BIC_CODE, accountInfoItem.getBicCode());

        String id = Long.toString(accountInfoItem.getId());
        String whereClause = AccountInfoItemsDBHelper.ID + " = " + id;
        db.update(AccountInfoItemsDBHelper.TABLE_NAME, values, whereClause, null);
    }

    /**
     * Delete a row from account_info_item-table specified by id.
     */
    public void deleteAccountInfoItem(long id) {
        System.out.println("Row from account_info_item deleted with id: " + id);
        db.delete(AccountInfoItemsDBHelper.TABLE_NAME, AccountInfoItemsDBHelper.ID + " = " + id,
                null);
    }

    /**
     * Fetch all rows from the account_info_item-table and make a list of AccountInfoItems from them
     *
     * @return a list of AccountInfoItems
     */
    public List<AccountInfoItem> getAllAccountInfoItems() {

        List<AccountInfoItem> accountInfoItems = new ArrayList<>();

        // Get all rows from account_info-table
        String orderByClause = AccountInfoItemsDBHelper.OWNER + " ASC";
        Cursor cursor = db.query(AccountInfoItemsDBHelper.TABLE_NAME, allColumns, null, null, null,
                null, orderByClause);

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

   /**
    * Build an AccountInfoItem-object from the row the cursor is pointing to from all
    * not-null-columns (bic_code is optional)
    *
    * @param cursor: A cursor pointing to a row from the account_info_item-table
    * @return an AccountInfoItem built from the row the cursor is pointing.
    */
    private AccountInfoItem cursorToAccountInfoItem(Cursor cursor) {

        if (cursor.isNull(3)) {
            return new AccountInfoItem(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
        } else {
            return new AccountInfoItem(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3));
        }
    }

    /**
     * Helper method to insert the given ContentValues-object in to the account_info_item-table
     *
     * @param values: A ContentValues-object containing values for a new account_info_item-row
     * @return an AccountInfoItem created from the newly added row
     */
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
