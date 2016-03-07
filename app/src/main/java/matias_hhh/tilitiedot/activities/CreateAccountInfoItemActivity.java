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
        setTitle(R.string.createBankAccountTitle);
        setContentView(R.layout.activity_create_bank_account);

        dbManager = new AccountInfoItemsDBManager(this);

        try {
            dbManager.open();
        } catch(SQLException err) {
            System.out.println(err);
        }
    }

    public void createBankAccountOnAddButtonClick(View view) {
        EditText ownerInput = (EditText) findViewById(R.id.ownerInput);
        EditText accountNumberInput = (EditText) findViewById(R.id.accountNumberInput);

        // Validate inputs
        boolean ownerInputHasErrors = InputValidation.validateName(this, ownerInput);
        boolean accountNumberInputHasErrors = InputValidation.validateIBANNumber(this,
                accountNumberInput);

        if (ownerInputHasErrors || accountNumberInputHasErrors) {
            return;
        }

        // If no errors, store the entry into db
        String owner = ownerInput.getText().toString();
        String accountNumber = accountNumberInput.getText().toString();
        dbManager.createAccountInfoItem(owner, accountNumber);

        // Return to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void openMainActivityOnCancelButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
