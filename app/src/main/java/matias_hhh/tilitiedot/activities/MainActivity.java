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

/**
* Main activity of the app. Shows a list of AccountInfoItems and a button to add more.
*/
public class MainActivity extends AppCompatActivity {

    private AccountInfoItemsDBManager dbManager;
    private List<AccountInfoItem> accountInfoItems;

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
        accountInfoItems = dbManager.getAllAccountInfoItems();

        // Add AccountInfoItem-objects to the RecycleView through the AccountInfoItemsAdapter
        AccountInfoItemsAdapter adapter = new AccountInfoItemsAdapter(accountInfoItems);
        RecyclerView accountInfoItemsView =
                (RecyclerView) findViewById(R.id.rv_accountinfoitems);
        accountInfoItemsView.setAdapter(adapter);
        accountInfoItemsView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * OnClick-listener. When the add-button is clicked, launch the CreateAccountInfoItem-activity
     */
    public void openBankAccountActivityOnFABClick(View view) {
        Intent intent = new Intent(this, CreateAccountInfoItemActivity.class);
        startActivity(intent);
    }
}
