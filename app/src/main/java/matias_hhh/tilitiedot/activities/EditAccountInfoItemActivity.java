package matias_hhh.tilitiedot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLException;

import matias_hhh.tilitiedot.R;
import matias_hhh.tilitiedot.models.AccountInfoItem;
import matias_hhh.tilitiedot.models.AccountInfoItemsDBManager;
import matias_hhh.tilitiedot.utils.IBANFormatTextWatcher;
import matias_hhh.tilitiedot.utils.InputValidator;

/**
 * Created by Matias on 12.3.2016.
 */
public class EditAccountInfoItemActivity extends AppCompatActivity {

    private AccountInfoItemsDBManager dbManager;

    long id;
    EditText ownerInput;
    EditText accountNumberInput;
    EditText bicCodeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.editaccountinfoitem_title);

        // Use the same layout that is used in CreateAccountInfoItem-activity
        setContentView(R.layout.activity_create_bank_account);

        dbManager = new AccountInfoItemsDBManager(this);

        try {
            dbManager.open();
        } catch (SQLException err) {
            System.out.println(err);
        }

        ownerInput = (EditText) findViewById(R.id.edittext_owner);
        accountNumberInput = (EditText) findViewById(R.id.edittext_accountnumber);
        bicCodeInput = (EditText) findViewById(R.id.edittext_biccode);


        // Get the input texts from the intent
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("ID");
        ownerInput.setText(extras.getString("OWNER"));
        accountNumberInput.setText(extras.getString("ACCOUNT_NUMBER"));
        bicCodeInput.setText(extras.getString("BIC_CODE"));


        // Change the add button's text and onClick
        Button addButton = (Button) findViewById(R.id.button_add);
        addButton.setText(getText(R.string.edit));
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAccountInfoItemOnEditButtonClick(view);
            }
        });

        // Add text formatting watcher to accountNumber-input
        accountNumberInput.addTextChangedListener(new IBANFormatTextWatcher());
    }

    public void editAccountInfoItemOnEditButtonClick(View view) {

        // Validate inputs, if errors, set error messages and go no further
        InputValidator validator = new InputValidator(this);
        if (!validator.allInputsAreValid(ownerInput, accountNumberInput, bicCodeInput)) {
            return;
        }

        // If inputs are valid, store the entry into db
        String owner = ownerInput.getText().toString().trim();
        String accountNumber = accountNumberInput.getText().toString().replace(" ", "");
        String bicCode = bicCodeInput.getText().toString().trim();

        // Make an AccountInfoItem-object (bic-code is optional)
        AccountInfoItem accountInfoItem;
        if (bicCode.length() == 0) {
            accountInfoItem = new AccountInfoItem(id, owner, accountNumber);
        } else {
            accountInfoItem = new AccountInfoItem(id, owner, accountNumber, bicCode);
        }

        dbManager.updateAccountInfoItem(accountInfoItem);

        // Return to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left_to_right, R.anim.slide_from_right_to_left);

    }

    public void openMainActivityOnCancelButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left_to_right, R.anim.slide_from_right_to_left);
    }
}
