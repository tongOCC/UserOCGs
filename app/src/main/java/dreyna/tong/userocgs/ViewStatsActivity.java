package dreyna.tong.userocgs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewStatsActivity extends AppCompatActivity {

    private TextView personalStatsPoundsTextView;
    private TextView personalStatsMoneyEarnedTextView;
    private TextView personalStatsEnergySavedTextView;
    private TextView personalStatsNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stats);
        personalStatsEnergySavedTextView= (TextView) findViewById(R.id.EnergySavedTextView);
        personalStatsMoneyEarnedTextView=(TextView) findViewById(R.id.StatsAllMoneyEarned);
        personalStatsNameTextView=(TextView)findViewById(R.id.StatsNameTextView);
        personalStatsPoundsTextView= (TextView) findViewById(R.id.StatsTotalPoundsRecycledTextView);

        Intent intent= getIntent();
//
//        viewStatsIntent.putExtra("profileName", yourProfile.getName());
//        viewStatsIntent.putExtra("totalPounds", totalPounds);
//        viewStatsIntent.putExtra("totalMoneyEarned",totalMoneyEarned);
//        viewStatsIntent.putExtra("totalEnergySaved",TotalEnergySaved);

        personalStatsNameTextView.setText(getString(R.string.usernameForStats)+" "+intent.getStringExtra("profileName"));
        personalStatsPoundsTextView.setText(getString(R.string.yourStatsPounds)+ " " +intent.getStringExtra("totalPounds"));
        personalStatsMoneyEarnedTextView.setText(getString(R.string.yourStatsMoneyEarned)+" $"+  intent.getStringExtra("totalMoneyEarned"));
        personalStatsEnergySavedTextView.setText(intent.getStringExtra("totalEnergySaved"));

    }

    public void backToMainMenuFromStats(View view) {
        finish();
    }
}
