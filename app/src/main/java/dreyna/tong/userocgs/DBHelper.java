package dreyna.tong.userocgs;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

class DBHelper extends SQLiteOpenHelper {

    private Context mContext;
    static final String DATABASE_NAME = "RecyclingLOGG";
    private static final int DATABASE_VERSION = 1;

    private static final String PROFILE_TABLE = "Profiles";
    private static final String PROFILE_KEY_FIELD_ID = "_id";
    private static final String FIELD_USERNAME = "username";
    private static final String FIELD_MONEY_EARNED = "money_earned";
    private static final String FIELD_PASSWORD= "password";
    private static final String FIELD_TOTAL_RECYCLED = "total_recycled";

    // LOG DATABASE
    private static final String LOG_TABLE = "LOGG";
    private static final String LOG_KEY_FIELD_ID = "_id";
    private static final String FIELD_LOGUSERNAME = "username";
    private static final String FIELD_LOGMONEY_EARNED = "money_earned";
    private static final String FIELD_LOGDATE= "logdate";
    private static final String FIELD_LOGTOTAL_RECYCLED = "total_recycled";
    private static final String FIELD_URI_IMAGE_RECIEPT= "image_uri";

    /**
     * constructs a new DBhelper given a context
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    /**
     * creates the tables used in the database
     * @param database contains two databases the LOG_TABLE and PROFILE_TABLE
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        String createQuery = "CREATE TABLE " + PROFILE_TABLE + "("
                + PROFILE_KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_USERNAME + " TEXT, "
                + FIELD_PASSWORD+ " TEXT, "
                + FIELD_MONEY_EARNED + " REAL, "
                + FIELD_TOTAL_RECYCLED + " REAL" + ")";
        database.execSQL(createQuery);
//DATABASE FOR LOG
        createQuery = "CREATE TABLE " + LOG_TABLE + "("
                + LOG_KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_LOGUSERNAME + " TEXT, "
                + FIELD_LOGDATE+ " TEXT, "
                + FIELD_LOGMONEY_EARNED + " REAL, "
                + FIELD_LOGTOTAL_RECYCLED + " REAL, "
                + FIELD_URI_IMAGE_RECIEPT + " TEXT"+ ")";
        database.execSQL(createQuery);
    }

    /**
     * Any changes to the table, tables get dropped
     * @param database PROFILE_TABLE, LOG_TABLE
     * @param oldVersion previous data
     * @param newVersion new data
     */
    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + LOG_TABLE);
        onCreate(database);
    }


    /**
     * adds a new profile object to the database
     * @param profile stores a profile with name password,money earned, and total recycled
     */
    public void addProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_USERNAME, profile.getName());
        values.put(FIELD_PASSWORD, profile.getPassword());
        values.put(FIELD_MONEY_EARNED, profile.getMoneyEarned());
        values.put(FIELD_TOTAL_RECYCLED, profile.getRecycledTotal());
        db.insert(PROFILE_TABLE, null, values);

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    /**
     * gets all the profiles in the database
     * @return List<Profile>
     */
    public List<Profile> getAllProfile() {
        List<Profile> profileList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(
                PROFILE_TABLE,
                new String[]{PROFILE_KEY_FIELD_ID, FIELD_USERNAME,FIELD_PASSWORD, FIELD_MONEY_EARNED, FIELD_TOTAL_RECYCLED},
                null,
                null,
                null, null, null, null);

        //COLLECT EACH ROW IN THE TABLE
        if (cursor.moveToFirst()) {
            do {
                Profile profile =
                        new Profile(cursor.getLong(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getDouble(3),
                                cursor.getDouble(4));
                profileList.add(profile);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return profileList;
    }

    /**
     * deletes a profile from the database
     * @param profile the profile to be deleted
     */
    public void deleteProfiles(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        // DELETE THE TABLE ROW
        db.delete(PROFILE_TABLE, PROFILE_KEY_FIELD_ID + " = ?",
                new String[]{String.valueOf(profile.getId())});
        db.close();
    }

    /**
     * deletes all profiles in the database
     */
    public void deleteAllProfiles() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROFILE_TABLE, null, null);
        db.close();
    }

    /**
     * updates the object's data within the database
     * @param profile the profile to be updated
     */
    public void updateProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_USERNAME, profile.getName());
        values.put(FIELD_PASSWORD,profile.getPassword());
        values.put(FIELD_MONEY_EARNED, profile.getMoneyEarned());
        values.put(FIELD_TOTAL_RECYCLED, profile.getRecycledTotal());

        db.update(PROFILE_TABLE, values, PROFILE_KEY_FIELD_ID + " = ?",
                new String[]{String.valueOf(profile.getId())});
        db.close();
    }

    /**
     * gets a profile its id
     * @param id the unique number for the profile
     * @return a Profile Object
     */
    public Profile getProfile(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                PROFILE_TABLE,
                new String[]{PROFILE_KEY_FIELD_ID,
                        FIELD_USERNAME,
                        FIELD_PASSWORD,
                        FIELD_MONEY_EARNED,
                        FIELD_TOTAL_RECYCLED},
                PROFILE_KEY_FIELD_ID + "= ?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Profile profile = new Profile(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getDouble(2),
                cursor.getDouble(3));

        cursor.close();
        db.close();
        return profile;
    }



// LOG DATABASE STUFF

    /**
     * adds a log to the Log database
     * @param logRecycle the log to be saved into the database
     */
    public void addLOG(Logger logRecycle) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_LOGUSERNAME, logRecycle.getName());
        values.put(FIELD_LOGDATE, logRecycle.getDate());
        values.put(FIELD_LOGMONEY_EARNED, logRecycle.getMoney_earned());
        values.put(FIELD_LOGTOTAL_RECYCLED, logRecycle.getTotal_recycled());
        values.put(FIELD_URI_IMAGE_RECIEPT,logRecycle.getReciept_image().toString());

        db.insert(LOG_TABLE, null, values);

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    /**
     * gets all the logs in the database
     * @return returns a list<Logger>
     */
    public List<Logger> getAllLogs() {
        List<Logger> loggerList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(
                LOG_TABLE,
                new String[]{LOG_KEY_FIELD_ID, FIELD_LOGUSERNAME,FIELD_LOGDATE, FIELD_LOGMONEY_EARNED, FIELD_LOGTOTAL_RECYCLED,FIELD_URI_IMAGE_RECIEPT},
                null,
                null,
                null, null, null, null);

        //COLLECT EACH ROW IN THE TABLE
        if (cursor.moveToFirst()) {
            do {
                Logger loggedLog =
                        new Logger(cursor.getLong(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getDouble(3),
                                cursor.getDouble(4),
                                Uri.parse(cursor.getString(5)));
               loggerList.add(loggedLog);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return loggerList;
    }

    /**
     * deletes a single log
     * @param loggedLog log to be deleted
     */
    public void deleteLog(Logger loggedLog) {
        SQLiteDatabase db = this.getWritableDatabase();

        // DELETE THE TABLE ROW
        db.delete(LOG_TABLE, LOG_KEY_FIELD_ID + " = ?",
                new String[]{String.valueOf(loggedLog.getId())});
        db.close();
    }

    /**
     * deletes all the logs in the database
     */
    public void deleteAllLogs() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(LOG_TABLE, null, null);
        db.close();
    }

    /**
     * updates the parameter logs data
     * @param loggedLog the log to be updated
     */
    public void updateLOGs(Logger loggedLog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_LOGUSERNAME, loggedLog.getName());
        values.put(FIELD_LOGDATE,loggedLog.getDate());
        values.put(FIELD_LOGMONEY_EARNED, loggedLog.getMoney_earned());
        values.put(FIELD_LOGTOTAL_RECYCLED, loggedLog.getTotal_recycled());
        values.put(FIELD_URI_IMAGE_RECIEPT,loggedLog.getReciept_image().toString());

        db.update(LOG_TABLE, values, LOG_KEY_FIELD_ID + " = ?",
                new String[]{String.valueOf(loggedLog.getId())});
        db.close();
    }

    /**
     * gets the log from its id
     * @param id the unique identifier of the log
     * @return the log at id
     */
    public Logger getLog(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                LOG_TABLE,
                new String[]{PROFILE_KEY_FIELD_ID,
                        FIELD_LOGUSERNAME,
                        FIELD_LOGDATE,
                        FIELD_LOGMONEY_EARNED,
                        FIELD_LOGTOTAL_RECYCLED},
                LOG_KEY_FIELD_ID + "= ?",
                new String[]{String.valueOf(id)},
                null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Logger loggedLog = new Logger(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getDouble(2),
                cursor.getDouble(3),
                Uri.parse(cursor.getString(4)));

        cursor.close();
        db.close();
        return loggedLog;
    }

}

