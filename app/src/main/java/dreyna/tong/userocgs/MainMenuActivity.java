package dreyna.tong.userocgs;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Main menu for the app that displays all the current users logs
 */
public class MainMenuActivity extends AppCompatActivity {
    private static final String TAG = MainMenuActivity.class.getSimpleName();
    private DBHelper db;
    private List<Profile> allProfiles;
    private ListView logListView;
    private RecyclerView recycleView;
    private List<Logger> allLogsList = new ArrayList<>();
    private List<Logger> filteredLogs = new ArrayList<>();
private boolean touchOn;
    private Uri imageUri;
    private LogListAdapter LogListAdapter;
    private String name;
    private Profile yourProfile;



    /**
     * starts and instantiates the app
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        logListView = (ListView) findViewById(R.id.listViewMainMenu);
        db = new DBHelper(this);

        allProfiles = db.getAllProfile();

        getLogsFromDB();
        LogListAdapter = new LogListAdapter(this, R.layout.profile_list_item, filteredLogs);
        logListView.setAdapter(LogListAdapter);



    }

    public void logOutOnClick(View view) {
        Toast.makeText(MainMenuActivity.this, R.string.successfullyloggedOut, Toast.LENGTH_SHORT).show();
        name = "";
        Intent logOut = new Intent(this, LoginActivity.class);
        logOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(logOut);

    }

    /**
     * handles when the user clicks on add recycle button send sthe profile data over for later use
     * @param view add recycle button
     */
    public void HandleRecycleButton(View view) {

        Intent intent = new Intent(this, NewLogActivity.class);
        // intent.putExtra("profileObject", yourProfile);
        intent.putExtra("profileName", yourProfile);
        startActivity(intent);

    }

    static class ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        ImageView personImageView;
    }


    /**
     * handles when the user clicks the view details button grabs the info of the specific log
     * and sends it to the details activity
     * @param view the item selected from the views
     */
    public void viewDetails(View view) {
        LinearLayout select = (LinearLayout) view;
        Logger selectedLog = (Logger) select.getTag();
        DecimalFormat twoPlaces = new DecimalFormat("0.00");
      String money=String.valueOf(twoPlaces.format(selectedLog.getMoney_earned()));
        String RecyceledTotal = String.valueOf(twoPlaces.format(selectedLog.getTotal_recycled()));
        String date = selectedLog.getDate().toString();
        String Uri = selectedLog.getReciept_image().toString();

        Intent detailsIntent = new Intent(this, RecycleDetailsActivity.class);
        detailsIntent.putExtra("money_earned",money);
        detailsIntent.putExtra("date", date);
        detailsIntent.putExtra("recycled_total", RecyceledTotal);
        detailsIntent.putExtra("Uri", Uri);
        startActivity(detailsIntent);
    }

    /**
     * handles when the user clicks the view stats button compiles all the data of thier logs from
     * the database to display all of thier stats
     * @param view view stats button
     */
    public void ViewStatsButtonOnClick(View view) {
        Intent viewStatsIntent= new Intent(this, ViewStatsActivity.class);
        Double totalPounds=0.0;
        Double totalMoneyEarned=0.0;
        Double EnergySavedAlluminum=0.0;
        Double EnergySavedGlass=0.0;
        Double EnergySavedHDPEplastic=0.0;
        Double EnergySavedPETplastic=0.0;
        Double GasAlluminum=0.0;
        Double GasGlass=0.0;
        Double GasHDPE=0.0;
        Double GasPET=0.0;
Double lbToTons;
            for(Logger Logs: filteredLogs)
                {
                    totalPounds+=Logs.getTotal_recycled();
                    totalMoneyEarned+=Logs.getMoney_earned();
    //TODO need energy save formula;

                }
                lbToTons = totalPounds/2000.0;

        EnergySavedAlluminum = lbToTons*1027.0;

        EnergySavedGlass = lbToTons*33.0;

        EnergySavedHDPEplastic = lbToTons*128.0;

        EnergySavedPETplastic = lbToTons*100.0;

        GasAlluminum = lbToTons*9.1;

        GasGlass = lbToTons*.293;

        GasHDPE = lbToTons*1.1;

        GasPET = lbToTons*.889;

        DecimalFormat twoPlaces = new DecimalFormat("0.00");
        String money=String.valueOf(twoPlaces.format(totalMoneyEarned));
        String RecyceledTotal = String.valueOf(twoPlaces.format(totalPounds));
        String energySaved=
                 getString(R.string.allText)+"\n"
                +getString(R.string.AlluminumGasText)+" "+ String.valueOf(twoPlaces.format(GasAlluminum))+" "+getString(R.string.Gallons)+"\n"
                +getString(R.string.GlassGasText)+" "+ String.valueOf(twoPlaces.format(GasGlass))+" "+getString(R.string.Gallons)+"\n"
                +getString(R.string.HDPEGasText)+" "+ String.valueOf(twoPlaces.format(GasHDPE))+" "+getString(R.string.Gallons)+"\n"
                +getString(R.string.PETGasText)+" "+ String.valueOf(twoPlaces.format(GasPET))+" "+getString(R.string.Gallons)+"\n"
                         +"-----------------------------------------------------------------"+"\n"
                +getString(R.string.CO2Conserved)+"\n\n"
                +getString(R.string.allText)+"\n"
                +getString(R.string.AlluminumCO2Text)+" "+ String.valueOf(twoPlaces.format(EnergySavedAlluminum))+" "+getString(R.string.MTCCO2)+"\n"
                +getString(R.string.GlassCO2Text)+" "+ String.valueOf(twoPlaces.format(EnergySavedGlass))+" "+getString(R.string.MTCCO2)+"\n"
                +getString(R.string.HDPECO2Text)+" "+ String.valueOf(twoPlaces.format(EnergySavedHDPEplastic))+" "+getString(R.string.MTCCO2)+"\n"
                +getString(R.string.PETCO2Text)+" "+ String.valueOf(twoPlaces.format(EnergySavedPETplastic))+" "+getString(R.string.MTCCO2)+"\n";


        viewStatsIntent.putExtra("profileName", yourProfile.getName());
        viewStatsIntent.putExtra("totalPounds", RecyceledTotal);
        viewStatsIntent.putExtra("totalMoneyEarned",money);
        viewStatsIntent.putExtra("totalEnergySaved",energySaved);
        startActivity(viewStatsIntent);
    }

    /**
     * method for getting logs from the database into all logs list
     */
    public void getLogsFromDB()
    {
        allLogsList = db.getAllLogs();

        Intent intent = getIntent();
        yourProfile = intent.getExtras().getParcelable("profileName");

        for (Logger l : allLogsList) {
            if (yourProfile.getName().equals(l.getName()))
                filteredLogs.add(l);
        }
    }

    /**
     * handles when the user clicsk the delete logs button
     * @param view the delte logs button
     */
    public void deleteLogMenuButtonClick(View view) {
        Intent intent= new Intent(this,DeleteLogActivity.class);

       intent.putExtra("profileName", yourProfile);

        startActivity(intent);
    }
}
