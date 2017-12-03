package dreyna.tong.userocgs;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
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
        Double TotalEnergySaved=0.0;

            for(Logger Logs: filteredLogs)
                {
                    totalPounds+=Logs.getTotal_recycled();
                    totalMoneyEarned+=Logs.getMoney_earned();
    //TODO need energy save formula;
                    TotalEnergySaved=-1.0;
                }
        DecimalFormat twoPlaces = new DecimalFormat("0.00");
        String money=String.valueOf(twoPlaces.format(totalMoneyEarned));
        String RecyceledTotal = String.valueOf(twoPlaces.format(totalPounds));
        String energySaved= String.valueOf(twoPlaces.format(TotalEnergySaved));

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
