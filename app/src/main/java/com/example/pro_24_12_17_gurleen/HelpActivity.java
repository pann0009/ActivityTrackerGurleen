package com.example.pro_24_12_17_gurleen;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class HelpActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // create menu toolbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_game:
                startActivity (new Intent(this, EnterNewActivityActivity.class));
                break;
            case R.id.activities:
                startActivity (new Intent (this, ListOfActivitiesActivity.class));
                break;
            case R.id.statistics:
                startActivity (new Intent (this, StatisticsActivity.class));
                break;
            case R.id.help:
                startActivity (new Intent (this, HelpActivity.class));
                final AlertDialog.Builder builder= new AlertDialog.Builder(this);
                builder.setTitle("help");
                builder.setMessage("Author: Gurleen Pannu");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                Dialog customDialog = builder.create();
                customDialog.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
