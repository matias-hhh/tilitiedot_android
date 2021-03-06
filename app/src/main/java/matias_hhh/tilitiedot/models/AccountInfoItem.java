package matias_hhh.tilitiedot.models;

/**
 * Java-representation of a row from account_info_item-table in SQLite db.
 */
public class AccountInfoItem {

    // DB Fields
    private long id;
    private String owner;
    private String accountNumber;
    private String bicCode;

    // Two constructors, since the bicCode is optional.
    public AccountInfoItem(long id, String owner, String accountNumber) {
        this.id  = id;
        this.owner = owner;
        this.accountNumber = accountNumber;
    }

    public AccountInfoItem(long id, String owner, String accountNumber, String bicCode) {
        this.id  = id;
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.bicCode = bicCode;
    }

    /* Getters & setters */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBicCode() {
        return bicCode;
    }

    public void setBicCode(String bicCode) {
        this.bicCode = bicCode;
    }

    /**
     *  Format the account number to have a space after every four characters
     *
     *  @return formatted accountNumber
     */
    public String getFormattedAccountNumber() {

        StringBuilder formatted = new StringBuilder(accountNumber);

        for (int i = 4; i < formatted.length(); i+=5) {
            formatted.insert(i, " ");
        }

        return formatted.toString();
    }
}
