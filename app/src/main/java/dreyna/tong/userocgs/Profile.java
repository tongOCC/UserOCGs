package dreyna.tong.userocgs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lelouch on 11/19/2017.
 */

public class Profile implements Parcelable {
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

    protected Profile(Parcel in) {
        id = in.readLong();
        name = in.readString();
        password = in.readString();
        moneyEarned = in.readDouble();
        recycledTotal = in.readDouble();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(password);
        parcel.writeDouble(moneyEarned);
        parcel.writeDouble(recycledTotal);
    }
}
