package com.example.pro_24_12_17_gurleen;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pro_24_12_17_gurleen.entities.UserActivity;

import java.util.List;

public class ListOfActivitiesActivity extends AppCompatActivity {


    private ListView listView;
    private List<UserActivity> activities;
    private UserActivity activityToDelete;

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
        setContentView(R.layout.activity_list_of_activities);
        //  get the List View
        this.listView = findViewById(R.id.listView);
        // show the list of activities
        refreshList();
    }


    private void refreshList() {
        try {
            // get all activities
            activities = new GetActivitiesTask().execute().get();
        } catch (Exception e) {
            // show error message
            Toast.makeText(this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // create list view using fragment ListViewDetailledAdapter
        listView.setAdapter(new ListViewDetailledAdapter(this, activities));
    }


    /**
     * delete activity with index i in the List activities
     */

    public void deleteActivity(int i) {
        try {
            // get the activity to delete
            activityToDelete = activities.get(i);
            // execute delete task
            new DeleteActivitiesTask().execute(activityToDelete).get();
            // show UNDO snackbar
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.list_of_activities_layout),
                    "Activity deleted", Snackbar.LENGTH_SHORT);
            // on click on snackbar undo the delete
            mySnackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        new AddUserActivity().execute(activityToDelete).get();
                        refreshList();
                    } catch (Exception e) {
                        Toast.makeText(ListOfActivitiesActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mySnackbar.show();
            // refresh the list of activities
            refreshList();
        } catch (Exception e) {
            Toast.makeText(this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /***
     * edit activity with index i
     */
    public void editActivity(int i) {
        // Go to edit activity with the parameter activity id
        Intent intent = new Intent(getBaseContext(), EditActivityActivity.class);
        intent.putExtra("id", activities.get(i).getId());
        startActivity(intent);
    }

    /***
     * edit activity with index i
     */
    public void detailActivity(int i) {
        // Go to edit activity with the parameter activity id
        Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
        intent.putExtra("id", activities.get(i).getId());
        startActivity(intent);
    }

    /**
     * The database operations in AsyncTask
     */
    private class GetActivitiesTask extends AsyncTask<Object, Integer, List<UserActivity>> {

        @Override
        protected List<UserActivity> doInBackground(Object... objects) {
            return ActivityTracker.db.userActivityDao().getAll();
        }
    }

    private class DeleteActivitiesTask extends AsyncTask<UserActivity, Integer, String> {

        @Override
        protected String doInBackground(UserActivity... activities) {
            ActivityTracker.db.userActivityDao().delete(activities[0]);
            return "deleted";
        }
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


    /**
     *
     */
}
