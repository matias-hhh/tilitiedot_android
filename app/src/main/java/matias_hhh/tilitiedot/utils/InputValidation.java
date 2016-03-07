package matias_hhh.tilitiedot.utils;

import android.content.Context;
import android.widget.EditText;

import matias_hhh.tilitiedot.R;

/**
 * Created by Matias on 6.3.2016.
 */
public class InputValidation {

    public static boolean validateName(Context context, EditText input) {

        boolean inputHasErrors = false;

        if (input.getText().length() == 0) {
            inputHasErrors = true;
            input.setError(context.getText(R.string.ownerEmpty));
        }

        return inputHasErrors;
    }

    public static boolean validateIBANNumber(Context context, EditText input) {

        boolean inputHasErrors = false;

        if (input.getText().length() == 0) {
            inputHasErrors = true;
            input.setError(context.getText(R.string.accountNumberEmpty));
        }

        return inputHasErrors;
    }
}
