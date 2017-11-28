package dreyna.tong.userocgs;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecycleDetailsActivity extends AppCompatActivity {
private ImageView recieptImage;
private TextView DetailsTextView;
private TextView RecycledTextView;
private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_details);
Intent intent= getIntent();
DetailsTextView.setText(intent.getStringExtra("money_earned"));
RecycledTextView.setText(intent.getStringExtra("recycled_total"));
imageUri=Uri.parse(intent.getStringExtra("Uri"));
recieptImage.setImageURI(imageUri);

    }

    public void backToMainMenu(View view) {
        finish();
    }
}
