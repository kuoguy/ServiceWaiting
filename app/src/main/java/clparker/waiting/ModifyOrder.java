package clparker.waiting;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class ModifyOrder extends AppCompatActivity {

    ObjectHandler objectHandler = new ObjectHandler();
    CloudDBConnector cloudDB = new CloudDBConnector();
    ArrayList<Order> orderArrayList = new ArrayList<>();
    ArrayList<Order_Line> orderLineArrayList = new ArrayList<>();
    ListView orderList;
    MobileServiceClient mClient;
    int navigation=0;

    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_order);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        spinner = (ProgressBar)findViewById(R.id.progressBar1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        try {
            mClient = new MobileServiceClient(
                    "https://cparkertest1.azurewebsites.net",
                    this
            );
        } catch (MalformedURLException e) {
            Log.d("CloudDB", "Failed to create mClient object using URL provided" + e.getMessage());
        }

        GetOrdersTask getOrdersTask = new GetOrdersTask();
        spinner.setVisibility(View.VISIBLE);
        getOrdersTask.execute();

    }

    public void setOrdersAdapter()
    {
        orderList= (ListView) findViewById(R.id.orderListView);
        orderList.setAdapter(new OrderAdapter(getActivity(), 0, orderArrayList));
        orderList.setClickable(true);
        orderList.setOnItemClickListener(mMessageClickedHandler);
    }

    public void setOrderLinesAdapter()
    {
        //Log.d("TestsMofo", "Line: "+orderLineArrayList.get(0).getOrder_Id());
        orderList.setAdapter(new OrderLineAdapter(getActivity(), 0, orderLineArrayList));
        orderList.setClickable(false);
    }

    public Activity getActivity(){return this;}

    AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            if (getResources().getResourceEntryName(parent.getId()).equals("orderListView")) {
                orderLineArrayList=orderArrayList.get(position).getLines();
                Log.d("LinesTest ", "number is: "+position);
                setOrderLinesAdapter();
                navigation++;
            }
        }
    };

    //initialises stuff to do with the custom action bar - uses R.menu.menu as template
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Handles button clicks in the actionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                //TextView recipeNameTV = (TextView) findViewById(R.id.editTextRecipeName);
                //String[] recipeName = new String[1]; // contains recipe name from onscreen editText
                //recipeName[0]=recipeNameTV.getText().toString();
                //Recipe[] recipeParam = new Recipe[1];
                //recipeParam[0]=global_recipeSelected;
                //SetRecipeLinesTask setRecipeLinesTask = new SetRecipeLinesTask();
                //setRecipeLinesTask.execute(recipeParam); //Passes parameter containing name
                return true;
            case R.id.action_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home: //Handle back button
                if(navigation==0)
                {
                    onBackPressed();
                }
                else
                {
                    setOrdersAdapter();
                    navigation--;
                }
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private class GetOrdersTask extends AsyncTask<String, Object, ArrayList<Order>>
    {
        @Override
        protected ArrayList<Order> doInBackground(String... params) {

            objectHandler.fetchOrders(mClient, cloudDB);
            return objectHandler.getOrderArrayList();
        }

        @Override
        protected void onPostExecute(ArrayList<Order> result) {
            orderArrayList = result;
            spinner.setVisibility(View.GONE);
            setOrdersAdapter();
        }

    }

}
