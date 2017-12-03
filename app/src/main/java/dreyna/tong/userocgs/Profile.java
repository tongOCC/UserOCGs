package dreyna.tong.userocgs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lelouch on 11/19/2017.
 */

/**
 * profile class that stores a unique id for database purposes, name, passwordm money earned, recycled
 * Total and is parcelable
 */
public class Profile implements Parcelable {
    private long id;
    private String name;
    private String password;
    private double moneyEarned;
    private double recycledTotal;

    /**
     * Constructor for a profile object
     * @param id
     * @param name
     * @param password
     * @param moneyEarned
     * @param recycledTotal
     */
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

    /**
     * Reads in the PRofile data to a parcel
     * @param in
     */
    protected Profile(Parcel in) {
        id = in.readLong();
        name = in.readString();
        password = in.readString();
        moneyEarned = in.readDouble();
        recycledTotal = in.readDouble();
    }

    /**
     * Creator method to make the profile class parceleable
     */
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

    /**
     * returns the unique id
     * @return id long
     */
    public long getId() {
        return id;
    }

    /**
     * returns the name of the profile
     * @return string name
     */
    public String getName() {
        return name;
    }

    /**
     * returns the password of the profile
     * @return string password
     */
    public String getPassword() {
        return password;
    }

    /**
     * returns the money earned
     * @return double money
     */
    public double getMoneyEarned() {
        return moneyEarned;
    }

    /**
     * returns the recyceld total
     * @return double recycled
     */
    public double getRecycledTotal() {
        return recycledTotal;
    }

    /**
     * sets the name of the profile the param
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets the money earned
     * @param moneyEarned
     */
    public void setMoneyEarned(double moneyEarned) {
        this.moneyEarned = moneyEarned;
    }

    /**
     * sets the recyceld total
     * @param recycledTotal
     */
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
