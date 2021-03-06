package clparker.waiting;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
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
    private Order currentOrder;
    private boolean ableRemove=false;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
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

        navigation=0; //Initialise grid navigation count

        currentOrder = new Order(0, 0); //Create a new order
        currentOrder.setStatus("New");

        GetRecipeCategory getRecipe = new GetRecipeCategory();
        spinner.setVisibility(View.VISIBLE);
        getRecipe.execute("");


    }
        private Activity getActivity(){return this;}

    public void setRecipeAdapter()
    {
        gridview = (GridView) findViewById(R.id.gridViewItems);
        gridview.setAdapter(new RecipeAdapter(getActivity(), 0, recipeArrayList));
        gridview.setOnItemClickListener(mMessageClickedHandler);

    }
    public void setCurrentOrderAdapter()
    {
        ArrayList<Order_Line> linesCurrent = currentOrder.getLines();

        ArrayList<Recipe> recipeLinesCurrent = new ArrayList<>();

        for(int count=0; count<linesCurrent.size(); count++)
            recipeLinesCurrent.add(linesCurrent.get(count).getLine());

        gridview = (GridView) findViewById(R.id.gridViewItems);
        gridview.setAdapter(new RecipeAdapter(getActivity(), 0, recipeLinesCurrent));
        gridview.setOnItemClickListener(mMessageClickedHandler);
        if(!ableRemove)
            ableRemove=true;
    }

    public void setRecipeCategoryAdapter()
    {
        gridview = (GridView) findViewById(R.id.gridViewItems);
        gridview.setAdapter(new RecipeCategoryAdapter(getActivity(), 0, recipeCategoryArrayList));
        gridview.setOnItemClickListener(mMessageClickedHandler);
        ableRemove=false;
    }

    public void setRecipeSubCategoryAdapter()
    {
        gridview = (GridView) findViewById(R.id.gridViewItems);
        gridview.setAdapter(new RecipeSubCategoryAdapter(getActivity(), 0, recipeSubCategoryArrayList));
        gridview.setOnItemClickListener(mMessageClickedHandler);
        ableRemove=false;
    }

    AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            if(getResources().getResourceEntryName(parent.getId()).equals("gridViewItems"))
            {
                if(ableRemove)
                {
                    int toRemove = position;
                    currentOrder.removeLine(position);
                    setCurrentOrderAdapter();

                }

                if(navigation==0)
                {
                    Recipe_Category[] selectedRecipeCategory= new Recipe_Category[1];
                    selectedRecipeCategory[0]=recipeCategoryArrayList.get(position);
                    GetRecipeSubCategory getRecipeSubCategory = new GetRecipeSubCategory();
                    spinner.setVisibility(View.VISIBLE);
                    getRecipeSubCategory.execute(selectedRecipeCategory);
                    navigation++;
                }
                else if(navigation==1)
                {
                    Recipe_SubCategory[] selectedRecipeSubCategory= new Recipe_SubCategory[1];
                    selectedRecipeSubCategory[0]=recipeSubCategoryArrayList.get(position);
                    GetRecipe getRecipe = new GetRecipe();
                    spinner.setVisibility(View.VISIBLE);
                    getRecipe.execute(selectedRecipeSubCategory);
                    navigation++;
                }
                else if(navigation==2)
                {
                    Recipe selectedRecipe = recipeArrayList.get(position);
                    Order_Line newLine = new Order_Line();
                    newLine.setLine(selectedRecipe);
                    newLine.setQuantity(1);
                    newLine.setOrder_Id(currentOrder.getOrder_id());
                    currentOrder.addLine(newLine);
                    Toast.makeText(getActivity(), "Item: "+selectedRecipe.getName()+" added to Order",
                            Toast.LENGTH_LONG).show();
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
                if(currentOrder.getSize()>0)
                {
                    Order[] toSave = new Order[1];
                    toSave[0] = currentOrder;
                    SetOrderTask setOrderTask = new SetOrderTask();
                    setOrderTask.execute(toSave);
                }
                else
                {
                    Toast.makeText(getActivity(), "Order Empty- Not Saved",
                            Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.action_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home: //Handle back button
                if(navigation==0)
                {
                    onBackPressed(); //Return to home screen
                }
                else if(navigation==1)
                {
                    navigation--;
                    setRecipeCategoryAdapter(); //Return to category view
                }
                else if(navigation==2)
                {
                    navigation--;
                    setRecipeSubCategoryAdapter(); //Return to subcategory view
                }
                else if(navigation==3)
                {
                    navigation--;
                    setRecipeAdapter();
                }
                return true;
            case R.id.action_order:
                //Intent intentOV = new Intent(this, OrderView.class);
                //startActivity(intentOV);
                navigation++;
                setCurrentOrderAdapter();
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
            spinner.setVisibility(View.GONE);
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
            spinner.setVisibility(View.GONE);
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
            spinner.setVisibility(View.GONE);
            setRecipeSubCategoryAdapter();
        }

    }

    private class SetOrderTask extends AsyncTask<Order[], Object, Boolean>
    {
        @Override
        protected Boolean doInBackground(Order[]...params)
        {
            Order toSave[] = params[0];
            cloudDB.addOrder(mClient, toSave[0]);
            return true;
        }
        @Override
        protected void onPostExecute(Boolean result)
        {
            Toast.makeText(getActivity(), "Order Saved",
                    Toast.LENGTH_LONG).show();
        }
    }

}
