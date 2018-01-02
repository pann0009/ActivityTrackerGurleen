package com.example.pro_24_12_17_gurleen;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class ActivityTracker extends AppCompatActivity {
    public static AppDatabase db;
    private ProgressBar progressBar;
    private Button statisticsButton;
    private Button listOfActivities;
    private Button addActivity;
    private TextView textViewProgress;
    private Button helpButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        this.progressBar = findViewById(R.id.progressBar);

        this.statisticsButton = findViewById(R.id.statisticsButton);
        this.helpButton = findViewById(R.id.helpButton);

        this.listOfActivities = findViewById(R.id.listOfActivities);
        this.addActivity = findViewById(R.id.addActivity);

        this.textViewProgress = findViewById(R.id.textViewProgress);
        this.progressBar.setVisibility(View.VISIBLE);

        this.textViewProgress.setVisibility(View.VISIBLE);

        addActionListners();
        connectToDB();
    }

    private void addActionListners() {
        this.addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EnterNewActivityActivity.class);
                startActivity(intent);
            }
        });

        this.listOfActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ListOfActivitiesActivity.class);
                startActivity(intent);
            }
        });

        this.statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityTracker.this, StatisticsActivity.class);
                startActivity(intent);
            }
        });

        this.helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityTracker.this, HelpActivity.class);
                startActivity(intent);
            }
        });
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


    private void connectToDB() {
        try {
            db = new ConnectToDataBaseTask().execute("activities").get();
        } catch (Exception e) {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.main_layout),
                    "Imposible to connect to the database", Snackbar.LENGTH_SHORT);
            mySnackbar.setAction("Close", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityTracker.this.finish();
                    System.exit(0);
                }
            });
            mySnackbar.show();

        }
    }


    private class ConnectToDataBaseTask extends AsyncTask<String, Integer, AppDatabase> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }


        @Override
        protected AppDatabase doInBackground(String... strings) {

            publishProgress(20);
            String dataBaseName = strings[0];
            publishProgress(40);
            AppDatabase database = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, dataBaseName).build();
            publishProgress(80);
            publishProgress(100);
            return database;


        }

        @Override
        public void onPostExecute(AppDatabase result){
            progressBar.setVisibility(View.INVISIBLE);

        }
    }

}
