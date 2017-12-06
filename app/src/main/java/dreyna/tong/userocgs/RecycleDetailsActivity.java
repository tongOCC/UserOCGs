package dreyna.tong.userocgs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

/**
 * handles when the user naviagates the RecycleDetails activity
 * displays the log data from the listView htat was selected
 */
public class RecycleDetailsActivity extends AppCompatActivity {
private ImageView recieptImage;
private TextView DetailsTextView;
private TextView RecycledTextView;
private TextView dateTextView;
private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_details);
        recieptImage= (ImageView) findViewById(R.id.DetailsImageView);
        DetailsTextView= (TextView) findViewById(R.id.DetailsTextView);
        RecycledTextView= (TextView) findViewById(R.id.DetailsRecycledDone);
        dateTextView=(TextView) findViewById(R.id.DetailsDateTextView);


Intent intent= getIntent();
DetailsTextView.setText("Recycled Earnings: $"+intent.getStringExtra("money_earned"));
RecycledTextView.setText("Amount Recycled in Pounds: "+intent.getStringExtra("recycled_total")+"LB(s)");
dateTextView.setText("Date: "+intent.getStringExtra("date"));
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(intent.getStringExtra("Uri")));
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 150, true);
            recieptImage.setImageBitmap(bitmap);
        } catch (IOException e) {
            Log.e("MovieListAdapter", "Error getting bitmap from: " + Uri.parse(intent.getStringExtra("Uri")));
        }
imageUri=Uri.parse(intent.getStringExtra("Uri"));
recieptImage.setImageURI(imageUri);

    }

    public void backToMainMenu(View view) {
        finish();
    }
}
