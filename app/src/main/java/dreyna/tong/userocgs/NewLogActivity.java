package dreyna.tong.userocgs;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NewLogActivity extends AppCompatActivity {
    private EditText PETplasticEditText;
    private EditText HDPEplasticEditText;
    private EditText BiMetalEditText;
    private EditText GlassEditText;
    private EditText AlluminumEditText;

    private Profile yourProfile;
    private TextView totalTextView;
    private ImageView logImageView;
    private String name;
    private Uri imageUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int GRANTED= PackageManager.PERMISSION_GRANTED;
    private static final int DENIED=PackageManager.PERMISSION_DENIED;

    private DBHelper db;

    private final double ALUMINUM_PRICE= 1.56;
    private final double GLASS_PRICE= .104;
    private final double BIMETAL_PRICE= .34;
    private final double PETPLASTIC_PRICE= 1.23;
    private final double HDPEPLASTIC_PRICE= .56;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_log);

        db = new DBHelper(this);
        PETplasticEditText= (EditText) findViewById(R.id.PETPlasticEditText);
        HDPEplasticEditText= (EditText) findViewById(R.id.HDPEPlasticEditText);
        BiMetalEditText= (EditText) findViewById(R.id.bimetalEditText);
        GlassEditText= (EditText) findViewById(R.id.glassEditText);
        AlluminumEditText= (EditText) findViewById(R.id.aluminumPoundsEditText);
        totalTextView=(TextView) findViewById(R.id.pricedAmountTextView);
        logImageView=(ImageView) findViewById(R.id.logRecieptImageView);
        imageUri=getUriFromResource(this,R.drawable.receipt_icon);
        logImageView.setImageURI(imageUri);
        Intent intent= getIntent();
       // yourProfile= intent.getExtras().getParcelable("profileObject");
        //if(yourProfile.equals(null))
       // {
          //  yourProfile.setName(intent.getStringExtra("backUpName"));
        yourProfile=intent.getExtras().getParcelable("profileName");
       // }

    }

    public void saveLogToDatabase(View view) {
        String username;
        //if(yourProfile.equals(null))
         username=yourProfile.getName();
        //else
            //username=yourProfile.getName();
        String date= DateFormat.getDateTimeInstance().format(new Date());
        Double money_earned, total_recycle;
        Double PETplasticLB, HDPEplasticLB, BimetalLB, glassLB, alumminumLB;
        if(PETplasticEditText.getText().toString().equals(""))
            PETplasticEditText.setText("0");

        if( HDPEplasticEditText.getText().toString().equals(""))
            HDPEplasticEditText.setText("0");

        if(BiMetalEditText.getText().toString().equals(""))
            BiMetalEditText.setText("0");;

        if(GlassEditText.getText().toString().equals(""))
            GlassEditText.setText("0");

        if(AlluminumEditText.getText().toString().equals(""))
            AlluminumEditText.setText("0");

        PETplasticLB=Double.parseDouble(PETplasticEditText.getText().toString());
        HDPEplasticLB=Double.parseDouble(HDPEplasticEditText.getText().toString());
        BimetalLB=Double.parseDouble(BiMetalEditText.getText().toString());
        glassLB=Double.parseDouble(GlassEditText.getText().toString());
        alumminumLB=Double.parseDouble(AlluminumEditText.getText().toString());

        DecimalFormat twoPlaces = new DecimalFormat("0.00");

        total_recycle= PETplasticLB
                +HDPEplasticLB
                +BimetalLB
                +glassLB+
                alumminumLB;



        money_earned=
                Double.parseDouble(PETplasticEditText.getText().toString())*PETPLASTIC_PRICE
                + Double.parseDouble(HDPEplasticEditText.getText().toString())*HDPEPLASTIC_PRICE
                + Double.parseDouble(BiMetalEditText.getText().toString())*BIMETAL_PRICE
                + Double.parseDouble(GlassEditText.getText().toString())*GLASS_PRICE
                +Double.parseDouble(AlluminumEditText.getText().toString())*ALUMINUM_PRICE;

        imageUri=getUriFromResource(this,R.drawable.receipt_icon);
        logImageView.setImageURI(imageUri);

        totalTextView.setText(String.valueOf(twoPlaces.format(money_earned)));
        Logger newLog= new Logger(username, date, money_earned,total_recycle, imageUri);
        final String displayTotal= "After Crv You are have earned: "+String.valueOf(money_earned);
        db.addLOG(newLog);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {

            }

        }, 10000);
        Intent intent= new Intent(this, MainMenuActivity.class);
        intent.putExtra("profileName", yourProfile );
        startActivity(intent);
    }
    public static Uri getUriFromResource(Context context, int resID)
    {
        Resources res= context.getResources();
        //Build a String in the form:
        //android.resource://edu.orangecoastcollege.cs273.petprotector
        String uri= ContentResolver.SCHEME_ANDROID_RESOURCE+"://"+ res.getResourcePackageName(resID)+ "/"
                + res.getResourceTypeName(resID)+"/"
                +res.getResourceEntryName(resID);
        // parse the string in o rder to construc a URI
        return Uri.parse(uri);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!= null)
        {
            //data= data from the Gallery Intent(the image
            imageUri= data.getData();
            logImageView.setImageURI(imageUri);
        }
    }

    /**
     * uses a camera intent to save the users receipt and changes the image of the icon to be saved
     * @param view
     */
    protected void pictureLogSave(View view) {
            List<String> permissionList= new ArrayList<>();
            // check each permission individually
            int hasCameraPerm= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if(hasCameraPerm==DENIED) {
                permissionList.add(Manifest.permission.CAMERA);
            }
            int hasReadPerm= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if(hasReadPerm==DENIED) {
                permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            int hasWritePerm= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(hasWritePerm==DENIED) {
                permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if(permissionList.size()>0)
            {
                //convert permsList ino an array
                String[] permsArray= new String[permissionList.size()];
                permissionList.toArray(permsArray);
                //ask
                //make a code number for third area, send
                ActivityCompat.requestPermissions(this, permsArray,1337 );


            }
            //make sure we have all permissions then start up the image Gallery:
            if(hasCameraPerm== GRANTED && hasReadPerm==GRANTED && hasWritePerm==GRANTED)
            {
                //lets open up gallery
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                // start activity for aour results the picture that the user selected
                startActivityForResult(takePictureIntent,1);

            }

        }

    }

