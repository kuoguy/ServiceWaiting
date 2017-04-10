package clparker.waiting;

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

    public ArrayList<Order> getOrderArrayList() {return orderArrayList;}
    public ArrayList<Recipe> getRecipeArrayList() {return recipeArrayList;}

    //Fetches all orders for this location and places in ordersArrayList
    public void fetchOrders(MobileServiceClient mClient, CloudDBConnector cloudDB)
    {
        List<Order> orders=cloudDB.getOrders(mClient, 1);
        orderArrayList.clear();

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

            }
        }

    }


}