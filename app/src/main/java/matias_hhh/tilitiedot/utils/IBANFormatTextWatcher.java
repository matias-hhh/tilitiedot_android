package matias_hhh.tilitiedot.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/**
 * TextWatcher that adds a space after every four characters in IBAN-input
 */
public class IBANFormatTextWatcher implements TextWatcher {

    private static final char space = ' ';
    private boolean spaceDeleted = false;
    private int spaceDeletedFrom;

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        // Flag the occasion when a space is about to be deleted
        if (count == 1 && (start + 1) % 5 == 0 && s.charAt(start) == space) {
            spaceDeleted = true;
            spaceDeletedFrom = start;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

        // When a space is being deleted by user, delete also the character before the space
        // since the user is not supposed to worry about the formatting spaces
        if (spaceDeleted) {
            spaceDeleted = false;
            s.delete(spaceDeletedFrom - 1, spaceDeletedFrom);

        }

        for (int i = 0; i <= s.length(); i++) {

            // Insert a space as every fifth character
            if ((i +  1) % 5 == 0) {

                if(i == s.length() || s.charAt(i) != space) {
                    s.insert(i, String.valueOf(space));
                }

            // Delete every space that is not every fifth
            } else {

                if (i < s.length() && s.charAt(i) == space) {
                    s.delete(i, i + 1);
                }
            }
        }
    }
}
