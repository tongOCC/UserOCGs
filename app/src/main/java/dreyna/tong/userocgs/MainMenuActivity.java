package dreyna.tong.userocgs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {
    private DBHelper db;
    private List<Profile> allProfiles;
    private ListView profileListView;

    private List<Logger> allLogsList= new ArrayList<>();
    private List<Logger> filteredLogs = new ArrayList<>();

    private LogListAdapter LogListAdapter;
private String name;
    private Profile yourProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        profileListView = (ListView) findViewById(R.id.listView);

        db = new DBHelper(this);

        allProfiles = db.getAllProfile();
        allLogsList= db.getAllLogs();

        Intent intent = getIntent();
        name= "tev";
        //Profile profile = intent.getExtras().getParcelable("profileName");

        for (Logger LOG : allLogsList) {
            if (name.equals(LOG.getName()))
                filteredLogs.add(LOG);
        }
               // }
              //  else
                //{


        LogListAdapter = new LogListAdapter(this, R.layout.profile_list_item,  filteredLogs);


        profileListView.setAdapter(LogListAdapter);
        LogListAdapter.notifyDataSetChanged();
        Toast.makeText(MainMenuActivity.this, allLogsList.toString(), Toast.LENGTH_SHORT).show();
    }

    public void logOutOnClick(View view) {
        Toast.makeText(MainMenuActivity.this, R.string.successfullyloggedOut, Toast.LENGTH_SHORT).show();
startActivity(new Intent(this,LoginActivity.class));

    }

    public void HandleRecycleButton(View view) {

        Intent intent= new Intent(this, NewLogActivity.class);
       // intent.putExtra("profileObject", yourProfile);
        intent.putExtra("backUpNames", name);
        startActivity(intent);

    }
}
