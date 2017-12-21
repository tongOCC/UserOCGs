package dreyna.tong.userocgs;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Class that handles when the user navigates to the contact info and hours activity
 */
public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    /**
     * handles when th user clicks on the back to maps button
     * @param view
     */
    public void backtoMap(View view) {

        Intent logOut = new Intent(this, MapActivity.class);
        logOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(logOut);
    }
}
