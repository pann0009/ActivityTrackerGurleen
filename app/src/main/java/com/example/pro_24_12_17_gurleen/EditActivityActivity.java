package com.example.pro_24_12_17_gurleen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pro_24_12_17_gurleen.entities.UserActivity;

import java.util.Date;

/**
 * Editiong the activity
 */
public class EditActivityActivity extends AppCompatActivity {
    private long acivityId;
    private UserActivity userActivity;
    private Button addActivityBtn;
    private EditText commentET;
    private EditText durationET;
    private Spinner typeOfActivitySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_activity);
        // get the activity to edit Id from intent
        acivityId = getIntent().getLongExtra("id", 1);
        try {
            // get the activity to ecit
            userActivity = new GetOneUserActivity().execute(acivityId).get();

            // init view components
            this.addActivityBtn = findViewById(R.id.addActivityBtn);
            this.commentET = findViewById(R.id.commentET);
            this.durationET = findViewById(R.id.durationET);
            this.typeOfActivitySpinner = findViewById(R.id.typeOfActivitySpinner);

            // set the previous values int the inputs
            this.commentET.setText(userActivity.getComment());
            this.durationET.setText(userActivity.getDuration() + "");

            // init the combobox
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activityTypes,
                    android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeOfActivitySpinner.setAdapter(adapter);

            // Button save Click event
            addActivityBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        // get the selected activity type
                        String typeOfActivity = EditActivityActivity.this.typeOfActivitySpinner.getSelectedItem().toString();
                        // get the comment
                        String comment = EditActivityActivity.this.commentET.getText().toString();
                        // get the duration
                        double duration = Double.parseDouble(EditActivityActivity.this.durationET.getText().toString());
                        // create the UserActivity
                        UserActivity activity = new UserActivity(typeOfActivity, duration, comment, new Date());
                        // set the Id of the edited activity
                        activity.setId(acivityId);
                        // execute the update
                        String result = new EditActivityActivity.EditUserActivity().execute(activity).get();

                        if (result.equals("success")) {
                            // the update is success
                            // go to list of activities
                            Intent intent = new Intent(getBaseContext(), ListOfActivitiesActivity.class);
                            startActivity(intent);
                            Toast.makeText(EditActivityActivity.this, result, Toast.LENGTH_SHORT).show();
                        } else {
                            // error durring update
                            // show the message error
                            Toast.makeText(EditActivityActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                        // exception durring update
                        // show the message error
                    } catch (NumberFormatException e) {
                        Toast.makeText(EditActivityActivity.this, "Please enter a duration", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(EditActivityActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } catch (Exception e) {
            Toast.makeText(EditActivityActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // create menu toolbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return true;
    }


    /**
     * Database operations in AsyncTasks
     */
    private class EditUserActivity extends AsyncTask<UserActivity, Integer, String> {
        @Override
        protected String doInBackground(UserActivity... userActivities) {
            try {
                ActivityTracker.db.userActivityDao().updateUsers(userActivities[0]);
                return "success";
            } catch (Exception e) {
                return "Error : " + e.getMessage();
            }
        }
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

    /**
     *
     */


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

}
