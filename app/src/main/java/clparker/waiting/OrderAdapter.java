package clparker.waiting;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Clown on 11/04/2017.
 */

public class OrderAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Order> items;

    public OrderAdapter(Context context, int resource, ArrayList<Order> objects) {
        super(context, resource, objects);
        this.context=context;
        this.items=objects;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Order itemObj=items.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.orderlist, null);

        TextView t1 = (TextView) view.findViewById(R.id.numberTextView);
        t1.setAllCaps(true);
        t1.setTextSize(22);
        TextView t2 = (TextView) view.findViewById(R.id.orderIdTextView);
        t2.setTextSize(16);
        TextView t3 = (TextView) view.findViewById(R.id.orderCreationTextView);
        t3.setTextSize(16);

        String numberString = Integer.toString(position);
        String idString = itemObj.getStatus();
        String dateString = itemObj.getCreatedTime();

        t1.setText(numberString);
        t2.setText(idString);
        t3.setText(dateString);

        return view;
    }
}
