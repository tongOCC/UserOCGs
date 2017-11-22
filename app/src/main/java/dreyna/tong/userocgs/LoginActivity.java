package dreyna.tong.userocgs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private DBHelper db;
    private List<Profile> allProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBHelper(this);

        username = (EditText) findViewById(R.id.usernameloginScreenEditText);
        password = (EditText) findViewById(R.id.passwordLogInEditText);

        allProfiles = db.getAllProfile();

    }

    /**
     * when the user clicks sign in button
     *
     * @param view
     */
    public void toMenuButtonClick(View view) {
/**TODO**/
        Intent intent = new Intent(this, MainMenuActivity.class);
        String usernameText = username.getText().toString();
        intent.putExtra("profileName", usernameText);
        startActivity(intent);
    }

    public void toCreateAccount(View view) {
        startActivity(new Intent(this, CreateProfileActivity.class));
    }
}
