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
    ArrayList<Recipe_Category> recipeCategoryArrayList = new ArrayList<Recipe_Category>();
    ArrayList<Recipe_SubCategory> recipeSubCategoryArrayList = new ArrayList<Recipe_SubCategory>();

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

        GetRecipeCategory getRecipe = new GetRecipeCategory();
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
        gridview.setAdapter(new RecipeCategoryAdapter(getActivity(), 0, recipeCategoryArrayList));
        gridview.setOnItemClickListener(mMessageClickedHandler);
    }

    public void setRecipeSubCategoryAdapter()
    {
        gridview = (GridView) findViewById(R.id.gridViewItems);
        gridview.setAdapter(new RecipeSubCategoryAdapter(getActivity(), 0, recipeSubCategoryArrayList));
        gridview.setOnItemClickListener(mMessageClickedHandler);
    }

    AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            if(getResources().getResourceEntryName(parent.getId()).equals("gridViewItems"))
            {
                if(navigation==0)
                {
                    Recipe_Category[] selectedRecipeCategory= new Recipe_Category[1];
                    selectedRecipeCategory[0]=recipeCategoryArrayList.get(position);
                    GetRecipeSubCategory getRecipeSubCategory = new GetRecipeSubCategory();
                    getRecipeSubCategory.execute(selectedRecipeCategory);
                    navigation++;
                }
                if(navigation==1)
                {
                    Recipe_SubCategory[] selectedRecipeSubCategory= new Recipe_SubCategory[1];
                    selectedRecipeSubCategory[0]=recipeSubCategoryArrayList.get(position);
                    GetRecipe getRecipe = new GetRecipe();
                    getRecipe.execute(selectedRecipeSubCategory);
                    navigation++;
                }
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

    private class GetRecipe extends AsyncTask<Recipe_SubCategory[], Object, ArrayList<Recipe>>
    {
        Recipe_SubCategory[] selectedSubCategory;
        @Override
        protected ArrayList<Recipe> doInBackground(Recipe_SubCategory[]... params) {
            selectedSubCategory=params[0];
            objectHandler.fetchRecipes(mClient, cloudDB);
            return objectHandler.getRecipeArrayList();
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> result) {
            ArrayList<Recipe> validRecipes = new ArrayList<>();
            for(int count=0; count<result.size(); count++)
            {
                Log.d("Findrecipes: ", "is: "+result.get(count).getRecipe_subcategory());
                if(result.get(count).getRecipe_subcategory().equals(selectedSubCategory[0].getName()))
                {
                    validRecipes.add(result.get(count));
                }
            }
            recipeArrayList = validRecipes;
            setRecipeAdapter();
        }

    }

    private class GetRecipeCategory extends AsyncTask<String, Object, ArrayList<Recipe_Category>>
    {
        @Override
        protected ArrayList<Recipe_Category> doInBackground(String... params) {

            objectHandler.fetchRecipeCategories(mClient, cloudDB);
            return objectHandler.getRecipeCategoryArrayList();
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe_Category> result) {
            recipeCategoryArrayList = result;
            setRecipeCategoryAdapter();
        }

    }

    private class GetRecipeSubCategory extends AsyncTask<Recipe_Category[], Object, ArrayList<Recipe_SubCategory>>
    {
        Recipe_Category[] selectedCategory;
        @Override
        protected ArrayList<Recipe_SubCategory> doInBackground(Recipe_Category[]... params) {

            selectedCategory=params[0];
            objectHandler.fetchRecipeSubCategories(mClient, cloudDB);
            return objectHandler.getRecipeSubCategoryArrayList();
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe_SubCategory> result) {
            ArrayList<Recipe_SubCategory> validSubCats = new ArrayList<>();
            for(int count=0; count<result.size(); count++)
            {
                if(result.get(count).getRecipe_category_name().equals(selectedCategory[0].getName()))
                {
                    validSubCats.add(result.get(count));
                }
            }
            recipeSubCategoryArrayList = validSubCats;
            setRecipeSubCategoryAdapter();
        }

    }

}
