package matias_hhh.tilitiedot.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.sql.SQLException;
import java.util.List;

import matias_hhh.tilitiedot.adapters.AccountInfoItemsAdapter;
import matias_hhh.tilitiedot.R;
import matias_hhh.tilitiedot.models.AccountInfoItem;
import matias_hhh.tilitiedot.models.AccountInfoItemsDBManager;
import matias_hhh.tilitiedot.utils.IAccountInfoItemOnClickMethods;

/**
* Main activity of the app. Shows a list of AccountInfoItems and a button to add more.
*/
public class MainActivity extends AppCompatActivity {

    private AccountInfoItemsDBManager dbManager;
    private AccountInfoItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect to the db
        dbManager = new AccountInfoItemsDBManager(this);
        try {
            dbManager.open();
        } catch(SQLException err) {
            System.out.println(err);
        }

        // Get all AccountInfoItems from db
        List<AccountInfoItem> accountInfoItems = dbManager.getAllAccountInfoItems();

        // Add AccountInfoItem-objects to the RecycleView through the AccountInfoItemsAdapter and
        // pass the implemented OnClick-methods to the adapter.
        adapter = new AccountInfoItemsAdapter(accountInfoItems,
                new AccountInfoItemOnClickMethods());

        RecyclerView accountInfoItemsRV = (RecyclerView) findViewById(R.id.rv_accountinfoitems);
        accountInfoItemsRV.setAdapter(adapter);
        accountInfoItemsRV.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * OnClick-method. When the add-button is clicked, launch the CreateAccountInfoItem-activity
     */
    public void openBankAccountActivityOnFABClick(View view) {
        Intent intent = new Intent(this, CreateAccountInfoItemActivity.class);
        startActivity(intent);
    }

    /**
     *  Implement OnClick-methods for the AccountInfoItems in the RecyclerView
     */
    class AccountInfoItemOnClickMethods implements IAccountInfoItemOnClickMethods {

        public void openEditActivityOnAccountInfoItemClick(View view) {
            System.out.println("Edit");
        }

        public void removeAccountInfoItemOnRemoveButtonClick(View view, int position) {

            // Delete item from db
            dbManager.deleteAccountInfoItem(adapter.getItemId(position));

            // Delete item from view
            adapter.removeItem(position);
        }
    }
}
