package dreyna.tong.userocgs;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lelouch on 11/20/2017.
 */

public class LogListAdapter extends ArrayAdapter<Logger> {
    private Context mContext;
    private List<Logger> mLogsList = new ArrayList<>();
    private int mResourceId;
    private LinearLayout logListLinearLayout;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private ImageView recieptImageView;

    public LogListAdapter(Context c, int rId, List<Logger> logs) {
        super(c, rId, logs);
        mContext = c;
        mResourceId = rId;
        mLogsList = logs;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {


        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);
        Logger logs = mLogsList.get(pos);
        logListLinearLayout =
                (LinearLayout) view.findViewById(R.id.listItemLinearLayout);
        descriptionTextView   =
                (TextView) view.findViewById(R.id.profileListDescriptionTextView);
        nameTextView =
                (TextView) view.findViewById(R.id.profileListViewName);
        recieptImageView =
                (ImageView) view.findViewById(R.id.ProfileListImageView);

        nameTextView.setText(logs.getName());
        descriptionTextView.setText("Money Earned: "+String.valueOf(logs.getMoney_earned())
                +"\nTotal Recycled: "+String.valueOf(logs.getTotal_recycled()));
        recieptImageView.setImageURI(logs.getReciept_image());
        logListLinearLayout.setTag(logs);
        return view;
    }
}


