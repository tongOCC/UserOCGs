package dreyna.tong.userocgs;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
DetailsTextView.setText(intent.getStringExtra("money_earned"));
RecycledTextView.setText(intent.getStringExtra("recycled_total"));
dateTextView.setText(intent.getStringExtra("date"));
imageUri=Uri.parse(intent.getStringExtra("Uri"));
recieptImage.setImageURI(imageUri);

    }

    public void backToMainMenu(View view) {
        finish();
    }
}
