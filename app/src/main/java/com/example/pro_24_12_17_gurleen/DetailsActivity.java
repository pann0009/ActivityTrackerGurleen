package com.example.pro_24_12_17_gurleen;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pro_24_12_17_gurleen.entities.UserActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DetailsActivity extends AppCompatActivity {

    private UserActivity activity;
    private TextView id;
    private TextView date;
    private TextView type;
    private TextView durration;
    private TextView comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        long acivityId = getIntent().getLongExtra("id", 1);
        try {
            // get the activity to ecit
            activity = new DetailsActivity.GetOneUserActivity().execute(acivityId).get();
            id = findViewById(R.id.text_view_id);
            date = findViewById(R.id.text_view_date);
            type = findViewById(R.id.text_view_type);
            durration = findViewById(R.id.text_view_duration);
            comment = findViewById(R.id.text_view_comment);

            id.setText("Id : " + activity.getId());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            date.setText("Date : " + df.format(activity.getDate()));
            type.setText("Type : " + activity.getType());
            durration.setText("Duration : " + activity.getDuration() + " min");
            comment.setText("Comment " + activity.getComment());


        } catch (Exception e) {
            Toast.makeText(DetailsActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                startActivity(new Intent(this, EnterNewActivityActivity.class));
                break;
            case R.id.activities:
                startActivity(new Intent(this, ListOfActivitiesActivity.class));
                break;
            case R.id.statistics:
                startActivity(new Intent(this, StatisticsActivity.class));
                break;
            case R.id.help:
                startActivity(new Intent(this, HelpActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetOneUserActivity extends AsyncTask<Long, Integer, UserActivity> {


        @Override
        protected UserActivity doInBackground(Long... id) {
            try {
                return ActivityTracker.db.userActivityDao().selectById(id[0]);
            } catch (Exception e) {
                return null;
            }
        }
    }
}
