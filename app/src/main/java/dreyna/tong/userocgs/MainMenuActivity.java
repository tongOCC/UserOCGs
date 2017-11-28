package dreyna.tong.userocgs;

import android.content.Intent;
import android.net.Uri;
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
    private ListView logListView;

    private List<Logger> allLogsList= new ArrayList<>();
    private List<Logger> filteredLogs = new ArrayList<>();

    private Uri imageUri;
    private LogListAdapter LogListAdapter;
private String name;
    private Profile yourProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        logListView = (ListView) findViewById(R.id.listViewMainMenu);

        db = new DBHelper(this);

        allProfiles = db.getAllProfile();
        allLogsList= db.getAllLogs();

        Intent intent = getIntent();
        String intentGrabName= intent.getStringExtra("backUpName");
        name= "t";
        //Profile profile = intent.getExtras().getParcelable("profileName");

        for (Logger LOG : allLogsList) {
            if (name.equals(LOG.getName()))
                filteredLogs.add(LOG);
        }

        LogListAdapter = new LogListAdapter(this, R.layout.profile_list_item,  filteredLogs);
        logListView.setAdapter(LogListAdapter);
        Toast.makeText(MainMenuActivity.this, allLogsList.toString(), Toast.LENGTH_SHORT).show();
    }

    public void logOutOnClick(View view) {
        Toast.makeText(MainMenuActivity.this, R.string.successfullyloggedOut, Toast.LENGTH_SHORT).show();
        name="";
startActivity(new Intent(this,LoginActivity.class));

    }

    public void HandleRecycleButton(View view) {

        Intent intent= new Intent(this, NewLogActivity.class);
       // intent.putExtra("profileObject", yourProfile);
        intent.putExtra("backUpNames", name);
        startActivity(intent);

    }
}
