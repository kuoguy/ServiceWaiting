package clparker.waiting;

/**
 * Created by Clown on 04/04/2017.
 */

public class Recipe_SubCategory {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipe_category_id() {
        return recipe_category_id;
    }

    public void setRecipe_category_id(String recipe_category_id) {
        this.recipe_category_id = recipe_category_id;
    }

    String id;
    String name;
    String recipe_category_id;
}
