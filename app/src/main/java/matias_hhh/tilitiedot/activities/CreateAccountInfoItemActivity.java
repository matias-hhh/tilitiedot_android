package matias_hhh.tilitiedot.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.SQLException;

import matias_hhh.tilitiedot.utils.InputValidation;
import matias_hhh.tilitiedot.R;
import matias_hhh.tilitiedot.models.AccountInfoItemsDBManager;

public class CreateAccountInfoItemActivity extends AppCompatActivity {

    private AccountInfoItemsDBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.createaccountinfotiem_title);
        setContentView(R.layout.activity_create_bank_account);

        dbManager = new AccountInfoItemsDBManager(this);

        try {
            dbManager.open();
        } catch(SQLException err) {
            System.out.println(err);
        }
    }

    public void createAccountInfoItemOnAddButtonClick(View view) {
        EditText ownerInput = (EditText) findViewById(R.id.edittext_owner);
        EditText accountNumberInput = (EditText) findViewById(R.id.edittext_accountnumber);
        EditText bicCodeInput = (EditText) findViewById(R.id.edittext_biccode);

        // Validate inputs, set error messages if errors
        boolean ownerInputHasErrors = InputValidation.validateOwner(this, ownerInput);
        boolean accountNumberInputHasErrors = InputValidation.validateIBAN(this,
                accountNumberInput);
        boolean bicCodeInputHasErrors = InputValidation.validateBIC(this, bicCodeInput);


        // If errors, go no further
        if (!ownerInputHasErrors || !accountNumberInputHasErrors || !bicCodeInputHasErrors) {
            return;
        }

        // If no errors, store the entry into db
        String owner = ownerInput.getText().toString().trim();
        String accountNumber = accountNumberInput.getText().toString().trim();
        String bicCode = bicCodeInput.getText().toString().trim();

        boolean bicCodeInputIsEmpty = bicCode.length() == 0;

        if (bicCodeInputIsEmpty) {
            dbManager.createAccountInfoItem(owner, accountNumber);
        } else {
            dbManager.createAccountInfoItem(owner, accountNumber, bicCode);
        }

        // Return to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openMainActivityOnCancelButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
