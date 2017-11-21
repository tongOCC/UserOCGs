package dreyna.tong.userocgs;

/**
 * Created by Lelouch on 11/19/2017.
 */

public class Profile {
    private long id;
    private String name;
    private String password;
    private double moneyEarned;
    private double recycledTotal;

    public Profile(long id, String name, String password, double moneyEarned, double recycledTotal) {
        this.id=id;
        this.name = name;
        this.password = password;
        this.moneyEarned = moneyEarned;
        this.recycledTotal = recycledTotal;
    }

    /**
     * constructor for making a new account;
     * @param name
     * @param password
     * @param moneyEarned
     * @param recycledTotal
     */
    public Profile(String name, String password,double moneyEarned, double recycledTotal) {
        this.id=-1;
        this.name = name;
        this.password = password;
        this.moneyEarned = 0;
        this.recycledTotal = 0;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public double getMoneyEarned() {
        return moneyEarned;
    }

    public double getRecycledTotal() {
        return recycledTotal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoneyEarned(double moneyEarned) {
        this.moneyEarned = moneyEarned;
    }

    public void setRecycledTotal(double recycledTotal) {
        this.recycledTotal = recycledTotal;
    }
}
