package dreyna.tong.userocgs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

public class NewLogActivity extends AppCompatActivity {
    private EditText PETplasticEditText;
    private EditText HDPEplasticEditText;
    private EditText BiMetalEditText;
    private EditText GlassEditText;
    private EditText AlluminumEditText;
    private Profile yourProfile;
    private TextView totalTextView;
    private String name;

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
        Intent intent= getIntent();
       // yourProfile= intent.getExtras().getParcelable("profileObject");
        //if(yourProfile.equals(null))
       // {
          //  yourProfile.setName(intent.getStringExtra("backUpName"));
            name=(intent.getStringExtra("backUpName"));
       // }

    }

    public void saveLogToDatabase(View view) {
        String username;
        //if(yourProfile.equals(null))
         username=name;
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

        totalTextView.setText(String.valueOf(twoPlaces.format(money_earned)));
        Logger newLog= new Logger(username, date, money_earned,total_recycle);
        db.addLOG(newLog);
        startActivity(new Intent(this,MainMenuActivity.class));
    }
}
