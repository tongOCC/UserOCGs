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

public class ProfileListAdapter extends ArrayAdapter<Profile> {
    private Context mContext;
    private List<Profile> mGamesList = new ArrayList<>();
    private int mResourceId;

    public ProfileListAdapter(Context c, int rId, List<Profile> profiles) {
        super(c, rId, profiles);
        mContext = c;
        mResourceId = rId;
        mGamesList = profiles;
    }

    public View getView(int pos, View convertView, ViewGroup parent)
    {
        final Profile profile = mGamesList.get(pos);

        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);

        LinearLayout profileListLinearLayout =
                (LinearLayout) view.findViewById(R.id.linearLayout);
        ImageView profileListImageView =
                (ImageView) view.findViewById(R.id.ProfileListImageView);
        TextView profileListNameTextView =
                (TextView) view.findViewById(R.id.ProfileListDescriptionTextView);
        TextView profileListDescriptionTextView =
                (TextView) view.findViewById(R.id.profileListViewName);

        profileListLinearLayout.setTag(profile);
        profileListNameTextView.setText(profile.getName());
        profileListDescriptionTextView.setText("Money Earned: "+String.valueOf(profile.getMoneyEarned())+"\nTotal Recycled: "+String.valueOf(profile.getRecycledTotal()));

        return view;
    }
}


