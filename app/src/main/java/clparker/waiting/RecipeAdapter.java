package clparker.waiting;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Clown on 04/04/2017.
 */

public class RecipeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Recipe> items;

    public RecipeAdapter(Context context, int resource, ArrayList<Recipe> objects) {
        this.context=context;
        this.items=objects;
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Recipe itemObj=items.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recipegridlist, null);

        TextView t1 = (TextView) view.findViewById(R.id.name1);
        TextView t2 = (TextView) view.findViewById(R.id.name2);

        String nameString = itemObj.getName();
        String idString = itemObj.getRecipe_id();

        t1.setText(nameString);
        t2.setText(idString);

        return view;
    }
}
