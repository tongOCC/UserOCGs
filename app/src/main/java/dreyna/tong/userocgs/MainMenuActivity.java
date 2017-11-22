package dreyna.tong.userocgs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {
    private DBHelper db;
    private List<Profile> allProfiles;
    private ListView profileListView;
    // TODO: NOTE - you didnt instantiate your list, so you were adding to a list that hasnt been created yet
    private List<Profile> yourProfile = yourProfile = new ArrayList<>();
    private ProfileListAdapter profileListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        db = new DBHelper(this);
        db.deleteAllProfiles();
        db.addProfile(new Profile("bob", "password", 100, 100));
        allProfiles = db.getAllProfile();

        Intent intent = getIntent();
        String name = intent.getStringExtra("profileName");

        for (Profile p : allProfiles)
            if (p.getName().equals(name)) {
                yourProfile.add(p);
            }

        profileListAdapter = new ProfileListAdapter(this, R.layout.profile_list_item, yourProfile);
        profileListView = (ListView) findViewById(R.id.listView);

        profileListView.setAdapter(profileListAdapter);
        profileListAdapter.notifyDataSetChanged();
    }

    public void logOutOnClick(View view) {

    }
}
