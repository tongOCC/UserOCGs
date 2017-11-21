package dreyna.tong.userocgs;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

    //TASK: DEFINE THE DATABASE VERSION AND NAME  (DATABASE CONTAINS MULTIPLE TABLES)
    static final String DATABASE_NAME = "Recycling";
    private static final int DATABASE_VERSION = 1;

    //TASK: DEFINE THE FIELDS (COLUMN NAMES) FOR THE INSTRUCTORS TABLE
    private static final String PROFILE_TABLE = "Profile";
    private static final String PROFILE_KEY_FIELD_ID = "_id";
    private static final String FIELD_USERNAME = "first_name";
    private static final String FIELD_MONEY_EARNED = "last_name";
    private static final String FIELD_TOTAL_RECYCLED = "email";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String createQuery = "CREATE TABLE " + PROFILE_TABLE + "("
                + PROFILE_KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_USERNAME + " TEXT, "
                + FIELD_MONEY_EARNED + " REAL, "
                + FIELD_TOTAL_RECYCLED + " REAL" + ")";
        database.execSQL(createQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + PROFILE_TABLE);
        onCreate(database);
    }



    public void addProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_USERNAME, profile.getName());
        values.put(FIELD_MONEY_EARNED, profile.getMoneyEarned());
        values.put(FIELD_TOTAL_RECYCLED, profile.getRecycledTotal());

        db.insert(PROFILE_TABLE, null, values);

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    public List<Profile> getAllProfile() {
        List<Profile> profileList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(
                PROFILE_TABLE,
                new String[]{PROFILE_KEY_FIELD_ID, FIELD_USERNAME, FIELD_MONEY_EARNED, FIELD_TOTAL_RECYCLED},
                null,
                null,
                null, null, null, null);

        //COLLECT EACH ROW IN THE TABLE
        if (cursor.moveToFirst()) {
            do {
                Profile profile =
                        new Profile(cursor.getString(0),
                                cursor.getString(1),
                                cursor.getDouble(2),
                                cursor.getDouble(3));
                profileList.add(profile);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return profileList;
    }

    public void deleteProfiles(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        // DELETE THE TABLE ROW
        db.delete(PROFILE_TABLE, PROFILE_KEY_FIELD_ID + " = ?",
                new String[]{String.valueOf(profile.getId())});
        db.close();
    }

    public void deleteAllProfiles() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(PROFILE_TABLE, null, null);
        db.close();
    }

    public void updateProfile(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_USERNAME, profile.getName());
        values.put(FIELD_MONEY_EARNED, profile.getMoneyEarned());
        values.put(FIELD_TOTAL_RECYCLED, profile.getRecycledTotal());

        db.update(PROFILE_TABLE, values, PROFILE_KEY_FIELD_ID + " = ?",
                new String[]{String.valueOf(profile.getId())});
        db.close();
    }

    public Profile getProfile(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                PROFILE_TABLE,
                new String[]{PROFILE_KEY_FIELD_ID, FIELD_USERNAME, FIELD_MONEY_EARNED, FIELD_TOTAL_RECYCLED},
                PROFILE_KEY_FIELD_ID + "=?",
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

}



    //********** IMPORT FROM CSV OPERATIONS:  Courses, Instructors and Offerings
    //DONE:  Write the code for the import OfferingsFromCSV method.


   /* public boolean importInstructorsFromCSV(String csvFileName) {
        AssetManager am = mContext.getAssets();
        InputStream inStream = null;
        try {
            inStream = am.open(csvFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line;
        try {
            while ((line = buffer.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != 4) {
                    Log.d("OCC Course Finder", "Skipping Bad CSV Row: " + Arrays.toString(fields));
                    continue;
                }
                int id = parseInt(fields[0].trim());
                String lastName = fields[1].trim();
                String firstName = fields[2].trim();
                String email = fields[3].trim();
                addInstructor(new Profile(, lastName, firstName, email));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    **/




