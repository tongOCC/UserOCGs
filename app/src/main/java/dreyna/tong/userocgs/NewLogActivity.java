package dreyna.tong.userocgs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_log);
    }

    public void saveLogToDatabase(View view) {
        startActivity(new Intent(this,MainMenuActivity.class));
    }
}
