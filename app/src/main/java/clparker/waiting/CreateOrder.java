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
import android.widget.GridView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class CreateOrder extends AppCompatActivity {

    private MobileServiceClient mClient;

    private CloudDBConnector cloudDB = new CloudDBConnector();
    private ObjectHandler objectHandler = new ObjectHandler();
    ArrayList<Recipe> recipeArrayList = new ArrayList<Recipe>();

    private GridView gridview;
    private int navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

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

        navigation=0; //Initialise grid navigation count

        GetRecipe getRecipe = new GetRecipe();
        getRecipe.execute("");


    }
        private Activity getActivity(){return this;}

    public void setRecipeAdapter()
    {
        gridview = (GridView) findViewById(R.id.gridViewItems);
        gridview.setAdapter(new RecipeAdapter(getActivity(), 0, recipeArrayList));
        gridview.setOnItemClickListener(mMessageClickedHandler);

    }

    public void setRecipeCategoryAdapter()
    {
        gridview = (GridView) findViewById(R.id.gridViewItems);
        gridview.setAdapter(new RecipeAdapter(getActivity(), 0, recipeArrayList));
    }

    AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            if(getResources().getResourceEntryName(parent.getId()).equals("gridViewItems"))
            {

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
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private class GetRecipe extends AsyncTask<String, Object, ArrayList<Recipe>>
    {
        @Override
        protected ArrayList<Recipe> doInBackground(String... params) {

            objectHandler.fetchRecipes(mClient, cloudDB);
            return objectHandler.getRecipeArrayList();
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> result) {
            recipeArrayList = result;
            setRecipeAdapter();
        }

    }

}
