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
class CustomAdapterSubgeddit extends BaseAdapter {
    private final ArrayList<String> distances;
    private final ArrayList<String> names;
    private final Activity_Main activity;

    public CustomAdapterSubgeddit(Activity_Main activity, ArrayList<String> distances, ArrayList<String> names){
        this.activity = activity;
        this.distances = distances;
        this.names = names;
    }

    @Override
    public int getCount() {
        return distances.size();
    }

    @Override
    public Object getItem(int position) {
        return distances.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.subgeddit_item, parent, false);

        TextView title = (TextView) row.findViewById(R.id.subgeddit_title);
        TextView distance = (TextView) row.findViewById(R.id.subgeddit_distance);

        title.setText(names.get(position));
        distance.setText(distances.get(position));

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onItemClick(null, null, position, 0);
            }
        });

        return row;
    }
}
