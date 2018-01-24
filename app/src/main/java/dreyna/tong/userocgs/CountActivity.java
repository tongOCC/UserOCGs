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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class CountActivity extends AppCompatActivity {
    private Button aluminumSmallIncreaseButton;
    private Button aluminumSmallDecreaseButton;
    private Button aluminumLargeIncreaseButton;
    private Button alumunimLargeDecreaseButton;
    private Button petSmallIncreaseButton;
    private Button petSmallDecreaseButton;
    private Button petLargeIncreaseButton;
    private Button petLargeDecreaseButton;
    private Button hdpeSmallIncreaseButton;
    private Button hdpesmallDecreaseButton;
    private Button hdpeLargeIncreaseButton;
    private Button hdpeLargeDecreaseButton;
    private TextView aluminumSmallTextCount;
    private TextView aluminumSmallTotalTextView;
    private TextView aluminumLargeCountTextView;
    private TextView aluminumLargeTotal;
    private TextView petSmallCountText;
    private TextView petSmallTotal;
    private TextView petLargeCount;
    private TextView petLargeTotal;
    private TextView hdpeSmallCount;
    private TextView hdpeSmallTotal;
    private TextView hdpeLargeCount;
    private TextView hdpeLargeTotal;
    private TextView totalPrice;
    private Uri imageUri;
    private Button saveAndExit;
    private ImageView saveImageButton;
    private TextView updatedPricesTextView;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    private static final int DENIED = PackageManager.PERMISSION_DENIED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        aluminumSmallIncreaseButton = (Button) findViewById(R.id.AluminumSmallIncreaseButton);
        aluminumSmallDecreaseButton = (Button) findViewById(R.id.AluminumDescreaseButton);
        aluminumLargeIncreaseButton = (Button) findViewById(R.id.AluminumLargeIncreaseButton);
        alumunimLargeDecreaseButton = (Button) findViewById(R.id.AluminumDecreaseButton);
        petSmallIncreaseButton = (Button) findViewById(R.id.petSmallIncreaseButton);
        petSmallDecreaseButton = (Button) findViewById(R.id.perSmallDecreaseButton);
        petLargeIncreaseButton = (Button) findViewById(R.id.petLargeIncreaseButton);
        petLargeDecreaseButton = (Button) findViewById(R.id.petLargeDecreaseButton);
        hdpeLargeDecreaseButton = (Button) findViewById(R.id.hdpeLargeDecreaseButton);
        hdpeLargeIncreaseButton = (Button) findViewById(R.id.hdpeLargeIncreaseButton);
        hdpeSmallIncreaseButton = (Button) findViewById(R.id.hdpeSmallIncrease);
        hdpesmallDecreaseButton = (Button) findViewById(R.id.hdpeSmallDecreaseButton);
        saveAndExit = (Button) findViewById(R.id.saveAndExitButton);

        saveImageButton = (ImageView) findViewById(R.id.ImageButton);

        aluminumSmallTextCount = (TextView) findViewById(R.id.AluminumSmall);
        aluminumSmallTotalTextView = (TextView) findViewById(R.id.aluminumSmalltotal);
        aluminumLargeCountTextView = (TextView) findViewById(R.id.AluminumLarge);
        aluminumLargeTotal = (TextView) findViewById(R.id.AluminumLargeTotal);
        petSmallCountText = (TextView) findViewById(R.id.petPlasticeSmall);
        petSmallTotal = (TextView) findViewById(R.id.petSmallTotal);
        petLargeCount = (TextView) findViewById(R.id.petplasticeCpntLarge);
        petLargeTotal = (TextView) findViewById(R.id.petLargeTotal);
        hdpeSmallCount = (TextView) findViewById(R.id.hdpeSmallCount);
        hdpeSmallTotal = (TextView) findViewById(R.id.hdpeSmallotal);
        hdpeLargeCount = (TextView) findViewById(R.id.hpdeLargeCount);
        hdpeLargeTotal = (TextView) findViewById(R.id.hdpeLargeTotal);

        updatedPricesTextView = (TextView) findViewById(R.id.updatedPricesTextView);

        //Method call to updatePrices

    }

    public static Uri getUriFromResource(Context context, int resID) {
        Resources res = context.getResources();
        //Build a String in the form:
        //android.resource://edu.orangecoastcollege.cs273.petprotector
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + res.getResourcePackageName(resID) + "/"
                + res.getResourceTypeName(resID) + "/"
                + res.getResourceEntryName(resID);
        // parse the string in o rder to construc a URI
        return Uri.parse(uri);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            //data= data from the Gallery Intent(the image
            imageUri = data.getData();
            saveImageButton.setImageURI(imageUri);
        }
    }

    /**
     * uses a camera intent to save the users receipt and changes the image of the icon to be saved
     *
     * @param view
     */
    public void pictureLogSave(View view) {
        List<String> permissionList = new ArrayList<>();
        // check each permission individually
        int hasCameraPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (hasCameraPerm == DENIED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        int hasReadPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadPerm == DENIED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        int hasWritePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWritePerm == DENIED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionList.size() > 0) {
            //convert permsList ino an array
            String[] permsArray = new String[permissionList.size()];
            permissionList.toArray(permsArray);
            //ask
            //make a code number for third area, send
            ActivityCompat.requestPermissions(this, permsArray, 1337);


        }
        //make sure we have all permissions then start up the image Gallery:
        if (hasCameraPerm == GRANTED && hasReadPerm == GRANTED && hasWritePerm == GRANTED) {
            //lets open up gallery
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
            // start activity for aour results the picture that the user selected


        }

    }

    public void getFromWebsite(String link) {
        int x = 0;
        URL url;
        String line = null;
        String rules = "";


        try {
            url = new URL(link);

            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                if (line.contains("<p>") || line.contains("</p>")) {
                    line = line.replaceAll("<p>", "");
                    line = line.replaceAll("</p>", "");
                    if (line.contains("&nbsp"))
                        line = line.replaceAll("&nbsp", "");

                    rules += line + "\n";
                }

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
