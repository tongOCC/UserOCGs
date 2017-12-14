package dreyna.tong.userocgs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Home parent screen for the app that prompts the user to log in with credentials
 */
public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private DBHelper db;
    private List<Profile> allProfiles;
    private String name;
    private Animation mFromRightAnim;
    private Animation mFromBottomAnim;
    private Animation mFromLeftAnim;
    private Animation mFadeOutAnim;
    private ImageView mRecycleLogo;
    private ImageView mPirateImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.usernameloginScreenEditText);
        password = (EditText) findViewById(R.id.passwordLogInEditText);
        mPirateImage = (ImageView) findViewById(R.id.pirateImageView);
        mRecycleLogo = (ImageView) findViewById(R.id.recycleLogoImageView);

        mFromLeftAnim = AnimationUtils.loadAnimation(this, R.anim.in_from_left);
        mFromRightAnim = AnimationUtils.loadAnimation(this, R.anim.in_from_right);
        mFadeOutAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out_anim);

        username.setEnabled(false);
        password.setEnabled(false);
        mPirateImage.startAnimation(mFromRightAnim);
        mRecycleLogo.startAnimation(mFromLeftAnim);


        mPirateImage.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPirateImage.startAnimation(mFadeOutAnim);
                mRecycleLogo.startAnimation(mFadeOutAnim);


            }
        }, 1400);
        mRecycleLogo.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPirateImage.setVisibility(View.GONE);
                mRecycleLogo.setVisibility(View.GONE);
                username.setEnabled(true);
                password.setEnabled(true);
            }
        }, 2400);


//        mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
//        mRelativeLayout.startAnimation(mShakeAnim);

        db = new DBHelper(this);
       // db.deleteAllLogs();
       // db.deleteAllProfiles();


        allProfiles = db.getAllProfile();


      //  Toast.makeText(LoginActivity.this, allProfiles.toString(), Toast.LENGTH_SHORT).show();
       // for (Profile p : allProfiles) {
          // Toast.makeText(LoginActivity.this, p.getName()+" "+p.getPassword(), Toast.LENGTH_SHORT).show();
       // }
    }

    /**
     * when the user clicks sign in button checks for a valid log in
     *
     * @param view the log in button
     */
    public void toMenuButtonClick(View view) {
        allProfiles = db.getAllProfile();
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        boolean loginSuccess = false;
        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(LoginActivity.this, R.string.incorrect_input_Toast, Toast.LENGTH_SHORT).show();
        } else {
            for (Profile p : allProfiles) {
                if (p.getName().equals(usernameText) && p.getPassword().equals(passwordText)) {
                    name = "" + p.getName();
                    Intent intent = new Intent(this, MainMenuActivity.class);
                    intent.putExtra("profileName", p);
                    intent.putExtra("name", name);
                    loginSuccess = true;
                    resetViewText();
                    startActivity(intent);
                }
            }
            if (!loginSuccess) {
                resetViewText();
                Toast.makeText(LoginActivity.this, R.string.incorrect_input_Toast, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * resets the edit text views to blank
     */
    public void resetViewText()
    {
       username.setText("");
        password.setText("");
    }

    /**
     * handles when the user clicks the create account button
     * @param view create profile button
     */
    public void toCreateAccount(View view) {
        resetViewText();
        startActivity(new Intent(this, CreateProfileActivity.class));
    }

    /**\
     * handles when the user clicks find us button
     * @param view find Us button
     */
    public void toMapClick(View view) {
        Intent toMapIntent = new Intent(this, MapActivity.class);
        startActivity(toMapIntent);
    }
}
