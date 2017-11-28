package dreyna.tong.userocgs;

import android.net.Uri;

/**
 * Created by Lelouch on 11/22/2017.
 */

public class Logger  {
    private long id;
    private String name;
    private String date;
    private double money_earned;
    private double total_recycled;
    private Uri reciept_image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Logger(long id, String name, String date, double money_earned, double total_recycled, Uri reciept_image) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.money_earned = money_earned;
        this.total_recycled = total_recycled;
        this.reciept_image=reciept_image;
    }

    public Logger(String name, String date, double money_earned, double total_recycled, Uri reciept_image) {
        this.id= -1;
        this.name = name;
        this.date = date;
        this.money_earned = money_earned;
        this.total_recycled = total_recycled;
        this.reciept_image=reciept_image;
    }

    public Uri getReciept_image() {
        return reciept_image;
    }

    public void setReciept_image(Uri reciept_image) {
        this.reciept_image = reciept_image;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getMoney_earned() {
        return money_earned;
    }

    public void setMoney_earned(double money_earned) {
        this.money_earned = money_earned;
    }

    public double getTotal_recycled() {
        return total_recycled;
    }

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
}
