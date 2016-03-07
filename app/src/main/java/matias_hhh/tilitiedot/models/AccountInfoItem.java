package matias_hhh.tilitiedot.models;

/**
 * Java-representation of a row from account_info_item-table in SQLite db.
 */
public class AccountInfoItem {

    // DB Fields
    private long id;
    private String owner;
    private String accountNumber;

    public AccountInfoItem(long id, String owner, String accountNumber) {
        this.id  = id;
        this.owner = owner;
        this.accountNumber = accountNumber;
    }

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

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return owner;
    }
}
