package dreyna.tong.userocgs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class CreateProfileActivity extends AppCompatActivity {
    private EditText createAccountUsername;
    private EditText createAccountPassword;
    private EditText createAccountConfirmPassword;
    private DBHelper db;
    private List<Profile> allProfiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        db = new DBHelper(this);
        allProfiles=db.getAllProfile();

        createAccountUsername=(EditText) findViewById(R.id.createProfileUsernameEditText);
        createAccountPassword=(EditText) findViewById(R.id.createProfilePasswordEditText);
        createAccountConfirmPassword=(EditText)findViewById(R.id.createProfilePassword2EditText);

    }

    public void createAccountButtonClick(View view) {
        if(!createAccountUsername.getText().toString().equals("") ||
                !createAccountPassword.getText().toString().equals("") ||
                !createAccountConfirmPassword.getText().toString().equals(""))
        {
            String name= createAccountUsername.getText().toString();
            String password= createAccountPassword.getText().toString();
            String confirmPassword=createAccountConfirmPassword.getText().toString();
            if(!password.equals(confirmPassword))
                 {
                Toast.makeText(CreateProfileActivity.this, R.string.passwordMatchError, Toast.LENGTH_SHORT).show();
                createAccountPassword.setText("");
                createAccountConfirmPassword.setText("");
                 }
            else {
                boolean usernameExists=false;
                // add to the database if the username nad password are correct and create a new profile account
                for (Profile p : allProfiles) {
                    if (p.getName().equals(name)) {
                        Toast.makeText(CreateProfileActivity.this, R.string.username_taken_errorText, Toast.LENGTH_SHORT).show();
                        createAccountUsername.setText("");
                        usernameExists=true;
                    }
                }
                    if(!usernameExists) {
                        Profile newProfile = new Profile(name,password, 0, 0);
                        db.addProfile(newProfile);
                        Intent intent = new Intent(this, MainMenuActivity.class);
                        intent.putExtra("username", name);
                        startActivity(intent);
                    }
            }
        }
        else
            Toast.makeText(CreateProfileActivity.this, R.string.incorrect_input_Toast, Toast.LENGTH_SHORT).show();
    }
}
