package matias_hhh.tilitiedot.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SQLiteOpenHelper-subclass for account_info_item-table.
 */
public class AccountInfoItemsDBHelper extends SQLiteOpenHelper {

    // Constants for the table
    public static final String TABLE_NAME = "account_info_item";
    public static final String ID = "_id";
    public static final String OWNER = "owner";
    public static final String ACCOUNT_NUMBER = "account_number";

    // Constants for the db
    private static final String DATABASE_NAME = "account_info.db";
    private static final int DATABASE_VERSION = 3;


    // Table creation sql statement
    private static final String TABLE_CREATE = "create table " +
            TABLE_NAME + "(" +
            ID + " integer primary key autoincrement, " +
            OWNER + " text not null, " +
            ACCOUNT_NUMBER + " text not null);";

    public AccountInfoItemsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Inform about the changes
        Log.w(AccountInfoItemsDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");

        // Drop the table
        db.execSQL("drop table if exists " + TABLE_NAME);

        // Create table again.
        onCreate(db);
    }

}
