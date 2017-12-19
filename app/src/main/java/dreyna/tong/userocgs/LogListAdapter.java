package dreyna.tong.userocgs;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
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
    private Uri imageUri;
    private LinearLayout logListLinearLayout;
    private TextView nameTextView;
    private TextView descriptionTextView;
    private ImageView recieptImageView;
private String tev= "hi";
    /**
     * constructs a LogListAdapter
     *
     * @param c    this comes from activity that this gets userd
     * @param rId  the resource
     * @param logs log item
     */
    public LogListAdapter(Context c, int rId, List<Logger> logs) {
        super(c, rId, logs);
        mContext = c;
        mResourceId = rId;
        mLogsList = logs;
    }

    /**
     * inflates the view from the xml profile_item_xml
     *
     * @param pos
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int pos, View convertView, ViewGroup parent) {
        MainMenuActivity.ViewHolder holder;
        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    View view= inflater.inflate(R.layout.profile_list_item, null);
        Logger logs = mLogsList.get(pos);
        DecimalFormat twoPlaces = new DecimalFormat("0.00");
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.profile_list_item, null);
//            logListLinearLayout =
//                    (LinearLayout) convertView.findViewById(R.id.listItemLinearLayout);
//            holder = new MainMenuActivity.ViewHolder();
//            holder.nameTextView = (TextView) convertView.findViewById(R.id.profileListViewName);
//            holder.descriptionTextView = (TextView) convertView.findViewById(R.id.profileListDescriptionTextView);
//            holder.personImageView = (ImageView) convertView.findViewById(R.id.ProfileListImageView);
//            convertView.setTag(holder);
//        } else {
//            holder = (MainMenuActivity.ViewHolder) convertView.getTag();
//        }
//        DecimalFormat twoPlaces = new DecimalFormat("0.00");
//
//        Logger logs = mLogsList.get(pos);
//        imageUri=logs.getReciept_image();
//        holder.nameTextView.setText(logs.getName());
//        holder.descriptionTextView.setText("Date: " + logs.getDate() + "\nMoney Earned: $" + String.valueOf(twoPlaces.format(logs.getMoney_earned()))
//                + "\nTotal Recycled: LB(s) " + String.valueOf(logs.getTotal_recycled()));
//        if(checksIftheFileisThere(imageUri))
//        {
//            imageUri=getUriFromResource(mContext,R.drawable.markedfordeletion);
//            holder.personImageView.setImageURI(imageUri);
//            logListLinearLayout.setTag(logs);
//        }
//        else
//        holder.personImageView.setImageURI(logs.getReciept_image());
//        logListLinearLayout.setTag(logs);





        logListLinearLayout =
                (LinearLayout) view.findViewById(R.id.listItemLinearLayout);
        descriptionTextView =
                (TextView) view.findViewById(R.id.profileListDescriptionTextView);
        nameTextView =
                (TextView) view.findViewById(R.id.profileListViewName);
        //recieptImageView =
           //     (ImageView) view.findViewById(R.id.ProfileListImageView);

        nameTextView.setText(logs.getName());
        descriptionTextView.setText("Date: " + logs.getDate() + "\nMoney Earned: $" + String.valueOf(twoPlaces.format(logs.getMoney_earned()))
                + "\nTotal Recycled: LB(s) " + String.valueOf(logs.getTotal_recycled())+"\n\n");
//        try {
//            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), logs.getReciept_image());
//            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 150, true);
//            recieptImageView.setImageBitmap(bitmap);
//        } catch (IOException e) {
//            Log.e("MovieListAdapter", "Error getting bitmap from: " + logs.getReciept_image().toString(), e);
//        }
//            recieptImageView.setImageURI(logs.getReciept_image());
            logListLinearLayout.setTag(logs);
            return view;
        }
       // return convertView;

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
    public static boolean checksIftheFileisThere(Uri path)
    {
        String filePath= path.toString();
        File file= new File(filePath);
        if(file.exists())
            return true;
        return false;
    }
}


