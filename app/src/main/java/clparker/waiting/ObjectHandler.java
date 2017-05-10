package clparker.waiting;

import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clown on 02/01/2017.
 */

public class ObjectHandler {

    ArrayList<Order> orderArrayList = new ArrayList<>();
    //ArrayList<Staff> staffArrayList = new ArrayList<>();
    ArrayList<Item> itemArrayList = new ArrayList<>();
    ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    ArrayList<Recipe_Category> recipeCategoryArrayList = new ArrayList<>();
    ArrayList<Recipe_SubCategory> recipeSubCategoryArrayList = new ArrayList<>();

    public ArrayList<Order> getOrderArrayList() {return orderArrayList;}
    public ArrayList<Recipe> getRecipeArrayList() {return recipeArrayList;}
    public ArrayList<Recipe_Category> getRecipeCategoryArrayList(){return recipeCategoryArrayList;}
    public ArrayList<Recipe_SubCategory> getRecipeSubCategoryArrayList(){return recipeSubCategoryArrayList;}

    //Fetches all orders for this location and places in ordersArrayList
    public void fetchOrders(MobileServiceClient mClient, CloudDBConnector cloudDB)
    {
        List<Order> orders=cloudDB.getOrders(mClient, 0);
        orderArrayList.clear();
        Log.d("Tests", orders.get(0).getOrder_id());
//        Log.d("Tests2", orders.get(0).getLine(0).getOrder_Id());
        if(orders!=null)
        {
            for (int count = 0; count < orders.size(); count++) {
                orderArrayList.add(orders.get(count));

            }
        }

    }

    public void fetchRecipes(MobileServiceClient mClient, CloudDBConnector cloudDB)
    {
        List<Recipe> orders=cloudDB.getRecipes(mClient);
        recipeArrayList.clear();

        if(orders!=null)
        {
            for (int count = 0; count < orders.size(); count++) {
                recipeArrayList.add(orders.get(count));
                Log.d("Handler: ", "recipe is: "+orders.get(count).getName());
            }
        }

    }

    public void fetchRecipeCategories(MobileServiceClient mClient, CloudDBConnector cloudDB)
    {
        List<Recipe_Category> categories=cloudDB.getRecipeCategories(mClient);
        recipeCategoryArrayList.clear();

        if(categories!=null)
        {
            for (int count = 0; count < categories.size(); count++) {
                recipeCategoryArrayList.add(categories.get(count));
            }
        }
    }

    public void fetchRecipeSubCategories(MobileServiceClient mClient, CloudDBConnector cloudDB)
    {
        List<Recipe_SubCategory> categories=cloudDB.getRecipeSubCategories(mClient);
        recipeSubCategoryArrayList.clear();

        if(categories!=null)
        {
            for (int count = 0; count < categories.size(); count++) {
                recipeSubCategoryArrayList.add(categories.get(count));
            }
        }
    }


}