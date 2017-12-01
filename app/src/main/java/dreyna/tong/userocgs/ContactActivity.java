package dreyna.tong.userocgs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    public void backtoMap(View view) {

        Intent logOut = new Intent(this, MapActivity.class);
        logOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(logOut);
    }
}
