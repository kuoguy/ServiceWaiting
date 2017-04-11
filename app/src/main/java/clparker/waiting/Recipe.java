package clparker.waiting;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clown on 21/11/2016.
 */

public class Recipe {

    private boolean isNew;

    public boolean getIsNew(){return isNew;}
    public void setIsNew(boolean isNewB){isNew=isNewB;}

    private ArrayList<Recipe_Line> items = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name; //Sets name of this recipe

        if(!items.isEmpty()) //Checks lines
        {
            for(int lineCount=0; lineCount<items.size(); lineCount++)
            {
                items.get(lineCount).setRecipeName(this.getName()); //Changes name within lines
            }
        }
    }

    private String name;
    private String recipe_id;

    public String getId() {
        return id;
    }
    public String getRecipe_id(){return recipe_id;}

    public void setId(String id) {
        this.id = id;
    }
    public void setRecipe_id(String nId){recipe_id=nId;}

    public ArrayList<Item> getItems() //Fetches item objects from each of the recipe_lines
    {
        ArrayList<Item> result = new ArrayList<>();
        for(int count=0; count<items.size(); count++)
        {
            result.add(items.get(count).getItem());
        }
        return result;
    }

    public void setLines(List<Recipe_Line> nLines)
    {
        items.clear();
        for(int count=0; count<nLines.size(); count++)
        {
            items.add(nLines.get(count));
        }
    }

    public void setItems(ArrayList<Item> itemsIn) //Sets items for recipe_lines in this recipe
    {
        for(int count=0; count<itemsIn.size(); count++)
        {
            Recipe_Line tempRL = new Recipe_Line();
            tempRL.setItem(itemsIn.get(count));
            tempRL.setIsNew(true);
            tempRL.setRecipeName(this.getName());
            tempRL.setItem_name(itemsIn.get(count).getItemName());
            tempRL.setItemSubCategory(itemsIn.get(count).getItemSubCategory());
            items.add(tempRL);
        }
    }

    /*
    public void setLines(List<Recipe_Line> linesIn, ItemHandler handler)
    {
        for(int count = 0; count<linesIn.size(); count++)
        {

            linesIn.get(count).setItem(handler.findItemPrecise(linesIn.get(count).getItem_name(), linesIn.get(count).getItemSubCategory()));
            this.items.add(linesIn.get(count));
            Log.d("RecipeLines", "stuff is: "+this.getItem(count).getItemName());
        }
        //this.items=linesIn;
    }
*/
    public void setItem (Item itemIn)
    {
        Recipe_Line tempRL = new Recipe_Line();
        tempRL.setItem(itemIn);
        tempRL.setIsNew(true);
        tempRL.setRecipeName(this.getName());
        tempRL.setItem_name(itemIn.getItemName());
        tempRL.setItemSubCategory(itemIn.getItemSubCategory());
        items.add(tempRL);
    }

    public Recipe_Line getRecipeLine(int element){return items.get(element);}
    public void addRecipeLine(Recipe_Line recipeLineNew){items.add(recipeLineNew);}

    public ArrayList<Recipe_Line> getRecipeLines(){return items;}

    public ArrayList<Recipe_Line> getNewRecipeLines()
    {
        ArrayList<Recipe_Line> result = new ArrayList<>();
        for(int count=0; count<items.size(); count++)
        {
            if(items.get(count).getIsNew()==true)
                result.add(items.get(count));
        }
        return result;
    }

    private String id;

    public String getRecipe_subcategory() {
        return recipe_subcategory;
    }

    public void setRecipe_subcategory(String recipe_category) {
        this.recipe_subcategory = recipe_category;
    }

    private String recipe_subcategory="";

    public Item getItem(int count) //finds item using id in items array
    {
        return items.get(count).getItem();
    }
    public Item getItem(String itemName) //Finds item using string name
    {
        Item foundItem=null;
        for(int count=0; count<items.size(); count++)
        {
            if(items.get(count).getItem().getItemName()==itemName)
                foundItem = items.get(count).getItem();
        }

        if(foundItem!=null)
            return foundItem;
        else
            Log.d("Recipe", "getItem string not found");

        return foundItem;
    }

    {
        this.setRecipe_subcategory("Default");
    }

}
