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

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Activities per month
 */
public class StatisticsActivity extends AppCompatActivity {

    private Map<String, Double> activities;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        try {
            // get All activities of the month
            List<UserActivity> allActivities = new StatisticsActivity.GetActivitiesTask().execute(-1).get();
            List<UserActivity> allActivitiesPreviousMonth = new StatisticsActivity.GetActivitiesTask().execute(-2).get();
            // group activities per Type and sum
            activities = createStatisticActivityList(allActivities);
            // create activities per type text
            String textRunning = "Running" + "(" + activities.get("Running") + " min)";
            String textWalking = "Walking" + "(" + activities.get("Walking") + " min)";
            String textBiking = "Biking" + "(" + activities.get("Biking") + " min)";
            String textSwimming = "Swimming" + "(" + activities.get("Swimming") + " min)";
            String textSkating = "Skating" + "(" + activities.get("Skating") + " min)";

            // get TextViews
            TextView running = findViewById(R.id.running);
            TextView walking = findViewById(R.id.walking);
            TextView biking = findViewById(R.id.biking);
            TextView swimming = findViewById(R.id.swimming);
            TextView skating = findViewById(R.id.skating);
            TextView numActivities = findViewById(R.id.num_activities);
            TextView numActivitiesPrevMonth = findViewById(R.id.num_activities_prev_month);
            // set text in TextVIew
            running.setText(textRunning);
            walking.setText(textWalking);
            biking.setText(textBiking);
            swimming.setText(textSwimming);
            skating.setText(textSkating);
            double time = 0;
            double time1 = 0;

            for (UserActivity activity : allActivities) {
                time += activity.getDuration();
            }

           /** for (UserActivity activity : allActivitiesPreviousMonth) {
                time1 += activity.getDuration();
            }*/
            numActivities.setText("Time spent this month: " + time + " min");
           // numActivitiesPrevMonth.setText("Time spent last month: " + time1 + " min");

        } catch (Exception e) {
            Toast.makeText(this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private Map<String, Double> createStatisticActivityList(List<UserActivity> allActivities) {
        Map<String, Double> activityTypeDurationMap = new HashMap<String, Double>() {{
            put("Running", 0d);
            put("Walking", 0d);
            put("Biking", 0d);
            put("Swimming", 0d);
            put("Skating", 0d);
        }};

        for (UserActivity activity : allActivities) {
            String type = activity.getType();
            if (activityTypeDurationMap.containsKey(type)) {
                double oldValue = activityTypeDurationMap.get(type);
                activityTypeDurationMap.put(type, oldValue + activity.getDuration());
            }
        }

        return activityTypeDurationMap;
    }


    private class GetActivitiesTask extends AsyncTask<Integer, Integer, List<UserActivity>> {

        @Override
        protected List<UserActivity> doInBackground(Integer... month) {
            Date date = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, month[0]);
            return ActivityTracker.db.userActivityDao().getActivitiesHavingTimeStampgreaterThan(UserActivity.dateToTimestamp(c.getTime()));
        }
    }
}
