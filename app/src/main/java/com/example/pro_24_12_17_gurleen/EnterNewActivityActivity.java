package com.example.pro_24_12_17_gurleen;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class EnterNewActivityActivity extends AppCompatActivity {

    private Button addActivityBtn;
    private EditText commentET;
    private EditText durationET;
    private Spinner typeOfActivitySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_new_activity);
        this.addActivityBtn = findViewById(R.id.addActivityBtn);
        this.commentET = findViewById(R.id.commentET);
        this.durationET = findViewById(R.id.durationET);
        this.typeOfActivitySpinner = findViewById(R.id.typeOfActivitySpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.activityTypes,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfActivitySpinner.setAdapter(adapter);
        addActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String typeOfActivity = EnterNewActivityActivity.this.typeOfActivitySpinner.getSelectedItem().toString();
                    String comment = EnterNewActivityActivity.this.commentET.getText().toString();
                    double duration = Double.parseDouble(EnterNewActivityActivity.this.durationET.getText().toString());
                    UserActivity activity = new UserActivity(typeOfActivity, duration, comment, new Date());
                    String result = new EnterNewActivityActivity.AddUserActivity().execute(activity).get();
                    if (result.equals("success")) {
                        Intent intent = new Intent(getBaseContext(), ListOfActivitiesActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(EnterNewActivityActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(EnterNewActivityActivity.this, "Please enter a duration", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(EnterNewActivityActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return true;
    }


    private class AddUserActivity extends AsyncTask<UserActivity, Integer, String> {


        @Override
        protected String doInBackground(UserActivity... userActivities) {
            try {
                ActivityTracker.db.userActivityDao().insertAll(userActivities[0]);
                return "success";
            } catch (Exception e) {
                return "Error : " + e.getMessage();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_game:
                startActivity (new Intent (this, EnterNewActivityActivity.class));
                break;
            case R.id.activities:
                startActivity (new Intent (this, ListOfActivitiesActivity.class));
                break;
            case R.id.statistics:
                startActivity (new Intent (this, StatisticsActivity.class));
                break;
            case R.id.help:
                startActivity (new Intent (this, HelpActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
