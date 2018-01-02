package com.example.pro_24_12_17_gurleen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.pro_24_12_17_gurleen.entities.UserActivity;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Activities List Item Fragment
 */
public class ListViewDetailledAdapter extends BaseAdapter {

    private ListOfActivitiesActivity context;
    private int count;
    private List<UserActivity> activities;
    private Button deleteActivity;
    private Button editActivity;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


    public ListViewDetailledAdapter(ListOfActivitiesActivity context, List<UserActivity> activities) {
        this.context = context;
        this.count = activities.size();
        this.activities = activities;
    }


    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return activities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return activities.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // get the current row
        View row = inflater.inflate(R.layout.list_view_adapter_detailled, null);
        // set the title
        // the title is Type + durration
        TextView title = row.findViewById(R.id.titleListView);
        // The comment TextView
        TextView comment = row.findViewById(R.id.commentListView);

        String text = activities.get(i).getType() + " (" + activities.get(i).getDuration() + " min )   \t" + formatter.format(activities.get(i).getDate());
        title.setText(text);
        comment.setText(activities.get(i).getComment());
        // delete activity Button
        deleteActivity = row.findViewById(R.id.delete_activity);
        deleteActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               context.deleteActivity(i);
            }
        });
        //edit activity Button
        editActivity = row.findViewById(R.id.edit_activity);
        editActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.editActivity(i);
            }
        });

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.detailActivity(i);
            }
        });
        return row;
    }
}
