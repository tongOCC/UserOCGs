package dreyna.tong.userocgs;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.R.attr.path;

public class ViewStatsActivity extends AppCompatActivity {

    private TextView personalStatsPoundsTextView;
    private TextView personalStatsMoneyEarnedTextView;
    private TextView personalStatsEnergySavedTextView;
    private TextView personalStatsNameTextView;
    private EditText emailEditText;
    private EditText subjectEditText;
    private String email;
    private String csvWriter;
    private DBHelper db;
    private Profile yourProfile;
    private List<Logger> allLogsList = new ArrayList<>();
    private List<Logger> filteredLogs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);
        personalStatsEnergySavedTextView = (TextView) findViewById(R.id.EnergySavedTextView);
        personalStatsMoneyEarnedTextView = (TextView) findViewById(R.id.StatsAllMoneyEarned);
        personalStatsNameTextView = (TextView) findViewById(R.id.StatsNameTextView);
        personalStatsPoundsTextView = (TextView) findViewById(R.id.StatsTotalPoundsRecycledTextView);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        Intent intent = getIntent();
        db = new DBHelper(this);
        allLogsList = db.getAllLogs();


        yourProfile = intent.getExtras().getParcelable("profileName");

        for (Logger l : allLogsList) {
            if (yourProfile.getName().equals(l.getName()))
                filteredLogs.add(l);

            csvWriter = "";
//        viewStatsIntent.putExtra("profileName", yourProfile.getName());
//        viewStatsIntent.putExtra("totalPounds", totalPounds);
//        viewStatsIntent.putExtra("totalMoneyEarned",totalMoneyEarned);
//        viewStatsIntent.putExtra("totalEnergySaved",TotalEnergySaved);

            personalStatsNameTextView.setText(getString(R.string.usernameForStats) + " " + intent.getStringExtra("Name"));
            personalStatsPoundsTextView.setText(getString(R.string.yourStatsPounds) + " " + intent.getStringExtra("totalPounds"));
            personalStatsMoneyEarnedTextView.setText(getString(R.string.yourStatsMoneyEarned) + " $" + intent.getStringExtra("totalMoneyEarned"));
            personalStatsEnergySavedTextView.setText(intent.getStringExtra("totalEnergySaved"));

        }
    }

    public void backToMainMenuFromStats(View view) {
        finish();
    }

    public void emailStats(View view) {
        String date = DateFormat.getDateTimeInstance().format(new Date());


            csvWriter = "";


            csvWriter += "Log Date: " + date + "\n\nCopy paste Below into text file and save as .CSV \n------------------------\n"+"username, date, money recycled, lbs of recycle\n";

            for (Logger logs : filteredLogs) {
                csvWriter += logs.getName() + ", "
                        + logs.getDate() + ", "
                        + logs.getMoney_earned() + ", "
                        + logs.getTotal_recycled() + "\n";
            }
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
            emailIntent.setType("vnd.android.cursor.dir/email");
            String to[] = {""};
            emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
// the attachment
            emailIntent.putExtra(Intent.EXTRA_STREAM, path);
// the mail subject
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "OCCGreen Save Log " +date.substring(0,12) );
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Username: "+yourProfile.getName()+ "\n"+ csvWriter);
            startActivity(Intent.createChooser(emailIntent, "Send email..."));

    }
}
