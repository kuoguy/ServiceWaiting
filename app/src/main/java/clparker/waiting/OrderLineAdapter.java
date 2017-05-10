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

public class OrderLineAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Order_Line> items;

    public OrderLineAdapter(Context context, int resource, ArrayList<Order_Line> objects) {
        super(context, resource, objects);
        this.context=context;
        this.items=objects;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Order_Line itemObj=items.get(position);
        Recipe lineRecipe = itemObj.getLine();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.orderlinelist, null);

        TextView t1 = (TextView) view.findViewById(R.id.numberTextView);
        t1.setAllCaps(true);
        t1.setTextSize(22);
        TextView t2 = (TextView) view.findViewById(R.id.recipeNameTextView);
        t2.setTextSize(16);
        TextView t3 = (TextView) view.findViewById(R.id.quantityTextView);
        t3.setTextSize(16);

        String numberString = Integer.toString(position);
        String idString = lineRecipe.getName();
        String dateString = Integer.toString(itemObj.getQuantity());

        t1.setText(numberString);
        t2.setText(idString);
        t3.setText(dateString);

        return view;
    }
}
