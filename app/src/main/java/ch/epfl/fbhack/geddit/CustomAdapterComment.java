package ch.epfl.fbhack.geddit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by fred on 19/04/15.
 */
public class CustomAdapterComment extends BaseAdapter {
    ArrayList<String> commentsBody;
    ArrayList<String> commentsScores;
    Activity_Comments activity;

    public CustomAdapterComment(Activity_Comments activity, ArrayList<String> commentsBody, ArrayList<String> commentsScores){
        this.activity = activity;
        this.commentsBody = commentsBody;
        this.commentsScores = commentsScores;
    }

    @Override
    public int getCount() {
        return commentsBody.size();
    }

    @Override
    public Object getItem(int position) {
        return commentsBody.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.comment_item, parent, false);

        TextView body = (TextView) row.findViewById(R.id.body);
        TextView score = (TextView) row.findViewById(R.id.score);

        body.setText(commentsBody.get(position));
        score.setText(commentsScores.get(position));

        //body.setOnClickListener(new View.OnClickListener() {
           // @Override
           // public void onClick(View v) {
          //      activity.onItemClick(null, null, position, 0);
         //   }
        //});

        return row;
    }
}
