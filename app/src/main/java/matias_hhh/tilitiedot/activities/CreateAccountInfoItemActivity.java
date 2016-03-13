package matias_hhh.tilitiedot.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.SQLException;

import matias_hhh.tilitiedot.utils.IBANFormatTextWatcher;
import matias_hhh.tilitiedot.utils.InputValidator;
import matias_hhh.tilitiedot.R;
import matias_hhh.tilitiedot.models.AccountInfoItemsDBManager;

public class CreateAccountInfoItemActivity extends AppCompatActivity {

    private AccountInfoItemsDBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.createaccountinfoitem_title);
        setContentView(R.layout.activity_create_bank_account);

        dbManager = new AccountInfoItemsDBManager(this);

        try {
            dbManager.open();
        } catch(SQLException err) {
            System.out.println(err);
        }

        // Add text formatting watcher to accountNumber-input
        EditText accountNumberInput = (EditText) findViewById(R.id.edittext_accountnumber);
        accountNumberInput.addTextChangedListener(new IBANFormatTextWatcher());
    }

    /**
     * onClick-onClickMethods. When add-button is clicked, the EditText-inputs are first validated
     * and if no errors, an AccountInfoItem is created from the input and saved to the db. If
     * errors, show errors to the user.
     */
    public void createAccountInfoItemOnAddButtonClick(View view) {
        EditText ownerInput = (EditText) findViewById(R.id.edittext_owner);
        EditText accountNumberInput = (EditText) findViewById(R.id.edittext_accountnumber);
        EditText bicCodeInput = (EditText) findViewById(R.id.edittext_biccode);

        // Validate inputs. If not valid, go no further
        InputValidator validator = new InputValidator(this);
        if (!validator.allInputsAreValid(ownerInput, accountNumberInput, bicCodeInput)) {
            return;
        }

        // If inputs are valid, store the entry into db
        String owner = ownerInput.getText().toString().trim();
        String accountNumber = accountNumberInput.getText().toString().replace(" ", "");
        String bicCode = bicCodeInput.getText().toString().trim();

        if (bicCode.length() == 0) {
            dbManager.createAccountInfoItem(owner, accountNumber);
        } else {
            dbManager.createAccountInfoItem(owner, accountNumber, bicCode);

        }

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
