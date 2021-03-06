package dreyna.tong.userocgs;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.NetworkOnMainThreadException;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Handles when the user navigates to the new log activity. allows the creation of new logs to be
 * saved into the database
 */
public class NewLogActivity extends AppCompatActivity{
    private EditText PETplasticEditText;
    private EditText HDPEplasticEditText;
    private EditText BiMetalEditText;
    private EditText GlassEditText;
    private EditText AlluminumEditText;
private Button ManualInputButton;
    private Profile yourProfile;
    private TextView totalTextView;
    private TextView updatedPricesText;
    private ImageView logImageView;
    private String name;
    private Uri imageUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int GRANTED= PackageManager.PERMISSION_GRANTED;
    private static final int DENIED=PackageManager.PERMISSION_DENIED;

    private DBHelper db;
private String result="";
    double ALUMINUM_PRICE= 1.60;
    double GLASS_PRICE= .102;
    double BIMETAL_PRICE= .37;
    double PETPLASTIC_PRICE= 1.24;
    double HDPEPLASTIC_PRICE=0.57;

    double ALUMINUM_PRICE_MANUAL= 0.05;
    double GLASS_PRICE_MANUAL= .05;
    double BIMETAL_PRICE_MANUAL= .37;
    double PETPLASTIC_PRICE_MANUAL= .05;
    double HDPEPLASTIC_PRICE_MANUAL=.05;


    int aluminumCount=0;
    int glassCount=0;
    int bimetalCount=0;
    int petPlasticCount=0;
    int hdpePlasticCount=0;
    private boolean ManualModeEngaged;

    String JSOUPAluminum="";
    String JSOUPGlass="";
    String JSOUPBiMetal="";
    String JSOUPPETPlastic="";
    String JSOUPHDPEPlastic="";

    /**
     * starts and instantiates views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_log);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

//        try {
//            getWebsite();
//        } catch (MalformedURLException e) {
//            Toast.makeText(NewLogActivity.this, "Could not update", Toast.LENGTH_SHORT).show();
//        }


        db = new DBHelper(this);
updatedPricesText=(TextView) findViewById(R.id.updatedPricesText);
        PETplasticEditText= (EditText) findViewById(R.id.PETPlasticEditText);
        HDPEplasticEditText= (EditText) findViewById(R.id.HDPEPlasticEditText);
        BiMetalEditText= (EditText) findViewById(R.id.bimetalEditText);
        GlassEditText= (EditText) findViewById(R.id.glassEditText);
        AlluminumEditText= (EditText) findViewById(R.id.aluminumPoundsEditText);
        ManualInputButton=(Button) findViewById(R.id.ManualInputButton);
        totalTextView=(TextView) findViewById(R.id.pricedAmountTextView);
        logImageView=(ImageView) findViewById(R.id.logRecieptImageView);
        imageUri=getUriFromResource(this,R.drawable.receipt_icon);

        logImageView.setImageURI(imageUri);
        Intent intent= getIntent();

        updatedPricesText.setText(result);
       // yourProfile= intent.getExtras().getParcelable("profileObject");
        //if(yourProfile.equals(null))
       // {
          //  yourProfile.setName(intent.getStringExtra("backUpName"));
        yourProfile=intent.getExtras().getParcelable("profileName");
       // }

    }

    /**
     * handles when the user clicks the save and exit views
     * @param view
     */
    public void saveLogToDatabase(View view) {
        String username;
        //if(yourProfile.equals(null))
        username = yourProfile.getName();
        //else
        //username=yourProfile.getName();
        String date = DateFormat.getDateTimeInstance().format(new Date());
        Double money_earned, total_recycle;
        Double PETplasticLB, HDPEplasticLB, BimetalLB, glassLB, alumminumLB;

        if (PETplasticEditText.getText().toString().equals("Input Lbs.")
                || PETplasticEditText.getText().toString().equals("Count of PETPlastic")
                || PETplasticEditText.getText().toString().equals(""))
            PETplasticEditText.setText("0");

        if (HDPEplasticEditText.getText().toString().equals("Input Lbs.")
                || HDPEplasticEditText.getText().toString().equals("Count of HDPEPlastic")
                || HDPEplasticEditText.getText().toString().equals(""))
            HDPEplasticEditText.setText("0");

        if (BiMetalEditText.getText().toString().equals("Input Lbs.")
                || BiMetalEditText.getText().toString().equals("Pounds of BiMetal")
                || BiMetalEditText.getText().toString().equals(""))
            BiMetalEditText.setText("0");
        ;

        if (GlassEditText.getText().toString().equals("Input Lbs.")
                || GlassEditText.getText().toString().equals("Count of Glass")
                || GlassEditText.getText().toString().equals("") )
            GlassEditText.setText("0");

        if (AlluminumEditText.getText().toString().equals("Input Lbs.")
                || AlluminumEditText.getText().toString().equals("Count of Aluminum")
                || AlluminumEditText.getText().toString().equals("") )
            AlluminumEditText.setText("0");

            PETplasticLB = Double.parseDouble(PETplasticEditText.getText().toString());
            HDPEplasticLB = Double.parseDouble(HDPEplasticEditText.getText().toString());
            BimetalLB = Double.parseDouble(BiMetalEditText.getText().toString());
            glassLB = Double.parseDouble(GlassEditText.getText().toString());
            alumminumLB = Double.parseDouble(AlluminumEditText.getText().toString());

            DecimalFormat twoPlaces = new DecimalFormat("0.00");

            total_recycle = PETplasticLB
                    + HDPEplasticLB
                    + BimetalLB
                    + glassLB +
                    alumminumLB;
            money_earned=0.0;
if(!ManualModeEngaged) {
    money_earned =
            Double.parseDouble(PETplasticEditText.getText().toString()) * PETPLASTIC_PRICE
                    + Double.parseDouble(HDPEplasticEditText.getText().toString()) * HDPEPLASTIC_PRICE
                    + Double.parseDouble(BiMetalEditText.getText().toString()) * BIMETAL_PRICE
                    + Double.parseDouble(GlassEditText.getText().toString()) * GLASS_PRICE
                    + Double.parseDouble(AlluminumEditText.getText().toString()) * ALUMINUM_PRICE;

}
else if(ManualModeEngaged) {
    money_earned =
            Double.parseDouble(PETplasticEditText.getText().toString()) * PETPLASTIC_PRICE_MANUAL
                    + Double.parseDouble(HDPEplasticEditText.getText().toString()) * HDPEPLASTIC_PRICE_MANUAL
                    + Double.parseDouble(BiMetalEditText.getText().toString()) * BIMETAL_PRICE_MANUAL
                    + Double.parseDouble(GlassEditText.getText().toString()) * GLASS_PRICE_MANUAL
                    + Double.parseDouble(AlluminumEditText.getText().toString()) * ALUMINUM_PRICE_MANUAL;
}
            totalTextView.setText(String.valueOf(twoPlaces.format(money_earned)));
            Logger newLog = new Logger(username, date, money_earned, total_recycle, imageUri);
            final String displayTotal = "After Crv You are have earned: " + String.valueOf(money_earned);
            db.addLOG(newLog);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                public void run() {

                }

            }, 10000);
            Intent intent = new Intent(this, MainMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            intent.putExtra("profileName", yourProfile);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    public void pictureLogSave(View view) {
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


            }

        }
    private void getWebsite() throws MalformedURLException {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://en.wikipedia.org/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
                   if(headline.attr("tr").equals("tr"))
                       JSOUPAluminum=headline.attr("tr");




        }
    }



    public void goToManualInputMenu(View view) {
       if(!ManualModeEngaged) {
           ManualModeEngaged= true;
           AlluminumEditText.setHint("Count of Aluminum");
           GlassEditText.setHint("Count of Glass");
           PETplasticEditText.setHint("Count of PETPlastic");
           HDPEplasticEditText.setHint("Count of HDPEPlastic");
           BiMetalEditText.setHint("Pounds of BiMetal");

       }
       else if (ManualModeEngaged) {
           ManualModeEngaged = false;
           AlluminumEditText.setHint("Input Lbs.");
           aluminumCount=0;
           glassCount=0;
           hdpePlasticCount=0;
           petPlasticCount=0;
           GlassEditText.setHint("Input Lbs.");
           PETplasticEditText.setHint("Input Lbs.");
           HDPEplasticEditText.setHint("Input Lbs.");
           BiMetalEditText.setHint("Input Lbs.");
       }
    }


    public void UpaluminumCount(View view) {
if(ManualModeEngaged==true)
{
aluminumCount++;
AlluminumEditText.setText(""+aluminumCount);
}
    }

    public void UpGlassCount(View view) {
        if(ManualModeEngaged==true)
        {
            glassCount++;
        GlassEditText.setText(""+glassCount);
        }
    }

    public void UpBiMetalCount(View view) {
        if(ManualModeEngaged==true)
        {
            bimetalCount++;
            BiMetalEditText.setText(""+bimetalCount);
        }
    }

    public void UpPlasticHDPECount(View view) {
        if(ManualModeEngaged==true)
        {
            hdpePlasticCount++;
            HDPEplasticEditText.setText(""+hdpePlasticCount);
        }
    }

    public void petPlasticCount(View view) {
        if(ManualModeEngaged==true)
        {
            petPlasticCount++;
           PETplasticEditText.setText( ""+petPlasticCount);
        }
    }
}

