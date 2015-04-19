package ch.epfl.fbhack.geddit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fred on 19/04/15.
 */
public class CustomAdapterThread extends BaseAdapter {
    ArrayList<String> threadsTitles;
    ArrayList<String> threadsScores;
    Activity_Threads activity;

    public CustomAdapterThread(Activity_Threads activity, ArrayList<String> threadsTitles, ArrayList<String> threadsScores){
        this.activity = activity;
        this.threadsTitles = threadsTitles;
        this.threadsScores = threadsScores;
    }

    @Override
    public int getCount() {
        return threadsTitles.size();
    }

    @Override
    public Object getItem(int position) {
        return threadsTitles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.thread_item, parent, false);

        TextView title = (TextView) row.findViewById(R.id.title);
        TextView score = (TextView) row.findViewById(R.id.score);

        title.setText(threadsTitles.get(position));
        score.setText(threadsScores.get(position));

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onItemClick(null, null, position, 0);
            }
        });

        row.findViewById(R.id.upvote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onUpvote(null, null, position, 0);
            }
        });

        row.findViewById(R.id.downvote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onDownvote(null, null, position, 0);
            }
        });

        return row;
    }
}
