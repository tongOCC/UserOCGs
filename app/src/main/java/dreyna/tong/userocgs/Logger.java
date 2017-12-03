package dreyna.tong.userocgs;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lelouch on 11/22/2017.
 */

/**
 * logger class the stores id name date money_earned, total_recycled, and Uri Image
 */
public class Logger implements Parcelable {
    private long id;
    private String name;
    private String date;
    private double money_earned;
    private double total_recycled;
    private Uri reciept_image;

    /**
     * method to make a parceable object
     * @param in
     */
    protected Logger(Parcel in) {
        id = in.readLong();
        name = in.readString();
        date = in.readString();
        money_earned = in.readDouble();
        total_recycled = in.readDouble();
        reciept_image = in.readParcelable(Uri.class.getClassLoader());
    }

    /**
     * creator method for making the the logger object parceable
     */
    public static final Creator<Logger> CREATOR = new Creator<Logger>() {
        @Override
        public Logger createFromParcel(Parcel in) {
            return new Logger(in);
        }

        @Override
        public Logger[] newArray(int size) {
            return new Logger[size];
        }
    };

    /**
     * gets the id
     * @return long id
     */
    public long getId() {
        return id;
    }


    /**
     * constructor for logger object
     * @param id unique identifier
     * @param name username data
     * @param date the date in string
     * @param money_earned double money
     * @param total_recycled double recyceld
     * @param reciept_image Uri image
     */
    public Logger(long id, String name, String date, double money_earned, double total_recycled, Uri reciept_image) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.money_earned = money_earned;
        this.total_recycled = total_recycled;
        this.reciept_image=reciept_image;
    }

    /**
     * default constructor for a logger object
     * @param name username data
     * @param date the date in string
     * @param money_earned double money
     * @param total_recycled double recyceld
     * @param reciept_image Uri image
     */
    public Logger(String name, String date, double money_earned, double total_recycled, Uri reciept_image) {
        this.id= -1;
        this.name = name;
        this.date = date;
        this.money_earned = money_earned;
        this.total_recycled = total_recycled;
        this.reciept_image=reciept_image;
    }

    /**
     * gets the loggers uri image
     * @return
     */
    public Uri getReciept_image() {
        return reciept_image;
    }

    /**
     * sets the Uri image
     * @param reciept_image the uri image to replace with
     */
    public void setReciept_image(Uri reciept_image) {
        this.reciept_image = reciept_image;
    }

    /**
     * gets the name on the logger
     * @return string name
     */
    public String getName() {
        return name;
    }

    /**
     * gets the date
     * @return string date
     */
    public String getDate() {
        return date;
    }

    /**
     * sets the date to the parameter
     * @param date the date to replace with
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * returns the money
     * @return
     */
    public double getMoney_earned() {
        return money_earned;
    }

    /**
     * sets the money earned
     * @param money_earned replaces the money value with the param
     */
    public void setMoney_earned(double money_earned) {
        this.money_earned = money_earned;
    }

    /**
     * returns the total recyceld of the logger
     * @return
     */
    public double getTotal_recycled() {
        return total_recycled;
    }

    /**
     * sets the total recyceled value in the logger
     * @param total_recycled
     */
    public void setTotal_recycled(double total_recycled) {
        this.total_recycled = total_recycled;
    }

    @Override
    public String toString() {
        return "Logger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", money_earned=" + money_earned +
                ", total_recycled=" + total_recycled +
                ", reciept_image=" + reciept_image +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * wirtes the logger into a parcel
     * @param parcel
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(name);
        parcel.writeString(date);
        parcel.writeDouble(money_earned);
        parcel.writeDouble(total_recycled);
        parcel.writeParcelable(reciept_image, i);
    }
}
