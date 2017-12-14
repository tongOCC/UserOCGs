package dreyna.tong.userocgs;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lelouch on 11/20/2017.
 */

/**
 * List adapter that handles listview population for the main menu and delete screen
 */
public class LogListAdapter extends ArrayAdapter<Logger> {
    private Context mContext;
    private List<Logger> mLogsList = new ArrayList<>();
    private int mResourceId;
    private LinearLayout logListLinearLayout;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private ImageView recieptImageView;
    private Uri mUri;

    /**
     * constructs a LogListAdapter
     * @param c this comes from activity that this gets userd
     * @param rId the resource
     * @param logs log item
     */
    public LogListAdapter(Context c, int rId, List<Logger> logs) {
        super(c, rId, logs);
        mContext = c;
        mResourceId = rId;
        mLogsList = logs;
    }

    /**
     *  inflates the view from the xml profile_item_xml
     * @param pos
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int pos, View convertView, ViewGroup parent) {

        DecimalFormat twoPlaces = new DecimalFormat("0.00");
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);
        Logger logs = mLogsList.get(pos);
        logListLinearLayout =
                (LinearLayout) view.findViewById(R.id.listItemLinearLayout);
        descriptionTextView =
                (TextView) view.findViewById(R.id.profileListDescriptionTextView);
        nameTextView =
                (TextView) view.findViewById(R.id.profileListViewName);
        recieptImageView =
                (ImageView) view.findViewById(R.id.ProfileListImageView);

        nameTextView.setText(logs.getName());
        descriptionTextView.setText("Date: " + logs.getDate() + "\nMoney Earned: $" + String.valueOf(twoPlaces.format(logs.getMoney_earned()))
                + "\nTotal Recycled: LB(s) " + String.valueOf(logs.getTotal_recycled()));


        mUri = logs.getReciept_image();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), mUri);
            bitmap = Bitmap.createScaledBitmap(bitmap,
                    (int) (bitmap.getWidth()*0.1),
                    (int) (bitmap.getHeight()*0.1), true);

            recieptImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            Log.e("MovieListAdapter", "Error getting bitmap from: " + mUri.toString(), e);
        }


            logListLinearLayout.setTag(logs);
            return view;
        }


}


