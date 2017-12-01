package dreyna.tong.userocgs;

import android.content.Intent;
import android.gesture.Gesture;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteLogActivity extends AppCompatActivity
         {

    private DBHelper db;

    private ListView DeleteListView;
    private List<Logger> allLogsList = new ArrayList<>();
    private List<Logger> filteredLogs = new ArrayList<>();
    private List<Logger> deletedLogs= new ArrayList<>();
    private Profile yourProfile;
    private LogListAdapter LogListAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_log);

        db = new DBHelper(this);
        allLogsList = db.getAllLogs();

        Intent intent = getIntent();

        yourProfile = intent.getExtras().getParcelable("profileName");

        for (Logger l : allLogsList) {
            if (yourProfile.getName().equals(l.getName()))
                filteredLogs.add(l);
        }

        DeleteListView= (ListView) findViewById(R.id.DeleteListView);
        LogListAdapter= new LogListAdapter(this, R.layout.profile_list_item, filteredLogs);
        DeleteListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            Logger deleteLog= (Logger) DeleteListView.getItemAtPosition(i);
                for(Logger logCheck: filteredLogs)
                 {
                     if(deleteLog.equals(logCheck))
                        {
                        filteredLogs.remove(deleteLog);
                        LogListAdapter.notifyDataSetChanged();
                        deletedLogs.add(deleteLog);
                        return true;
                        }
                 }
            return false;
     }
});

        DeleteListView.setAdapter(LogListAdapter);

    }

public void viewDetails(View v)
{

}

    public void saveChangesFromDelete(View view) {
      if(!deletedLogs.isEmpty() || deletedLogs.size()!=0) {
          for (Logger L : deletedLogs) {
              db.deleteLog(L);

          }
          Toast.makeText(DeleteLogActivity.this, R.string.successfullyDeletedSelectedItems, Toast.LENGTH_SHORT).show();

      }
      else
          Toast.makeText(DeleteLogActivity.this, R.string.failedDeletedSelectedItems, Toast.LENGTH_SHORT).show();
    }

             public void backToMainMenuFromDelete(View view) {

                 Intent backToMainMenu = new Intent(this, MainMenuActivity.class);
                 backToMainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 backToMainMenu.putExtra("profileName", yourProfile);
                 startActivity(backToMainMenu);
             }
         }
