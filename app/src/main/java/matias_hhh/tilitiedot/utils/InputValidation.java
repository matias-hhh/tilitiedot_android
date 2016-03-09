package matias_hhh.tilitiedot.utils;

import android.content.Context;
import android.widget.EditText;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import matias_hhh.tilitiedot.R;

/**
 * Utility class used for validating EditText inputs. Validation functions set errors to the inputs
 * and get error messages from res/values/strings.xml
 */
public class InputValidation {

    /**
     * Validation function for owner input.
     *
     * @param context: Context-object for getting error message strings.
     * @param input: EditText object for the owner input
     * @return true if no errors, false when errors
     */
    public static boolean validateOwner(Context context, EditText input) {


        String inputString = input.getText().toString().trim();

        if (inputString.length() == 0) {
            input.setError(context.getText(R.string.validate_owner_empty));
            return false;
        }

        return true;
    }

    /**
     * Validation function for IBAN-number input.
     *
     * @param context: Context-object for getting error message strings.
     * @param input: EditText object for the IBAN-number
     * @return true if no errors, false when errors
     */
    public static boolean validateIBAN(Context context, EditText input) {

        // Delete all formatting whitespaces too
        String inputString = input.getText().toString().replace(" ", "");

        if (inputString.length() == 0) {
            input.setError(context.getText(R.string.validate_accountnumber_empty));
            return false;
        }

        /*
        IBAN consists consists of:
        - country code, two capital letters A-Z
        - two check digits
        - basic bank account number (BBAN) up to 30 alphanumerical characters A-Z and 0-9
        */
        if (!Pattern.matches("^[A-Z]{2}\\d{2}[A-Z_0-9]{1,30}$", inputString)) {
            input.setError(context.getText(R.string.validate_accountnumber_invalidformat));
            return false;
        }

        String countryCode = inputString.substring(0, 2);
        if (inputString.length() != getIBANLength(countryCode)) {
            input.setError(context.getText(R.string.validate_accountnumber_invalidlength));
            return false;
        }

        if (!checkMod97(inputString)) {
            input.setError(context.getText(R.string.validate_accountnumber_invalidchecksum));
            return false;
        }

        return true;
    }

    /**
     * Validation function for BIC-code input.
     *
     * @param context: Context-object for getting error message strings.
     * @param input: EditText object for the BIC-code input
     * @return true if no errors, false when errors
     */
    public static boolean validateBIC(Context context, EditText input) {


        String inputString = input.getText().toString().trim();

        // No validation needed if no input
        if (inputString.length() == 0) {
            return true;
        }

        // First six characters have to be capital letters A-Z.
        if (!Pattern.matches("^[A-Z]{6}[A-Z_0-9]{2,5}$", inputString)) {
            input.setError(context.getText(R.string.validate_biccode_invalidformat));
            return false;
        }

        if (inputString.length() != 8 && inputString.length() != 11) {
            input.setError(context.getText(R.string.validate_biccode_invalidlength));
            return false;
        }

        return true;
    }

    /**
     * Check if give string is a valid IBAN-number by performing a modulo 97 operation.
     *
     * @param input: A string containing an IBAN-number
     * @return true if input is a valid IBAN
     */
    private static boolean checkMod97(String input) {

        // Move four first characters to the end of the string
        String firstFour = input.substring(0, 4);
        String rest = input.substring(4);
        String rearrangedInput = rest + firstFour;

        // Convert all characters to integers (A=10, B=11, C=12, etc, characters 0-9 have their
        // respectful integer values)
        String numericString = "";
        for (int i = 0; i < rearrangedInput.length(); i++) {
            char c = rearrangedInput.charAt(i);

            numericString += Integer.toString(Character.getNumericValue(c));
        }

        // Turn the numeric string to a big int (value > 64 bits)
        BigInteger checksum = new BigInteger(numericString);

        // Perform modulo 97- operation for the value. If the result equals one, the IBAN is valid.
        return checksum.mod(new BigInteger("97")).longValue() == 1;
    }

    /**
     * Get the length of IBAN in a specified country. Values updated 9.3.2016.
     *
     * WARNING: MAY CONTAIN ERRORS
     *
     * @param countryCode: two character country code eg. "FI", "SE", etc.
     * @return length of the IBAN in the specified country
     */
    private static int getIBANLength(String countryCode) {
        Map<String, Integer> IBANLengths = new HashMap<>();
        IBANLengths.put("AL", 28);
        IBANLengths.put("AD", 24);
        IBANLengths.put("AT", 20);
        IBANLengths.put("AZ", 28);
        IBANLengths.put("BH", 22);
        IBANLengths.put("BE", 16);
        IBANLengths.put("BA", 20);
        IBANLengths.put("BR", 29);
        IBANLengths.put("BG", 22);
        IBANLengths.put("CR", 21);
        IBANLengths.put("HR", 21);
        IBANLengths.put("CY", 28);
        IBANLengths.put("CZ", 24);
        IBANLengths.put("DK", 18);
        IBANLengths.put("DO", 28);
        IBANLengths.put("TL", 23);
        IBANLengths.put("EE", 20);
        IBANLengths.put("FO", 18);
        IBANLengths.put("FI", 18);
        IBANLengths.put("FR", 27);
        IBANLengths.put("GE", 22);
        IBANLengths.put("DE", 22);
        IBANLengths.put("GI", 23);
        IBANLengths.put("GR", 27);
        IBANLengths.put("GL", 18);
        IBANLengths.put("GT", 28);
        IBANLengths.put("HU", 28);
        IBANLengths.put("IS", 26);
        IBANLengths.put("IE", 22);
        IBANLengths.put("IL", 23);
        IBANLengths.put("IT", 27);
        IBANLengths.put("JO", 30);
        IBANLengths.put("KZ", 20);
        IBANLengths.put("XK", 20);
        IBANLengths.put("KW", 30);
        IBANLengths.put("LV", 21);
        IBANLengths.put("LB", 28);
        IBANLengths.put("LI", 21);
        IBANLengths.put("LT", 20);
        IBANLengths.put("LU", 20);
        IBANLengths.put("MK", 19);
        IBANLengths.put("MT", 31);
        IBANLengths.put("MR", 27);
        IBANLengths.put("MU", 30);
        IBANLengths.put("MC", 27);
        IBANLengths.put("MD", 24);
        IBANLengths.put("ME", 22);
        IBANLengths.put("NL", 18);
        IBANLengths.put("NO", 15);
        IBANLengths.put("PK", 24);
        IBANLengths.put("PS", 29);
        IBANLengths.put("PL", 28);
        IBANLengths.put("PT", 25);
        IBANLengths.put("QA", 29);
        IBANLengths.put("RO", 24);
        IBANLengths.put("SM", 27);
        IBANLengths.put("SA", 24);
        IBANLengths.put("RS", 22);
        IBANLengths.put("SK", 24);
        IBANLengths.put("SI", 19);
        IBANLengths.put("ES", 24);
        IBANLengths.put("SE", 24);
        IBANLengths.put("CH", 21);
        IBANLengths.put("TN", 24);
        IBANLengths.put("TR", 26);
        IBANLengths.put("AE", 23);
        IBANLengths.put("GB", 22);
        IBANLengths.put("VG", 24);
        return IBANLengths.get(countryCode);
    }
}
