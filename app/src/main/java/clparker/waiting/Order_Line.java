package clparker.waiting;

/**
 * Created by Clown on 02/01/2017.
 */

public class Order_Line {

    private String id;
    private String order_id;
    private Recipe line;
    private String recipe;
    private int quantity=0;

    public void setLine(Recipe nLine)
    {
        this.line=nLine;
        this.recipe=nLine.getRecipe_id();
    }
    public void setQuantity(int nQuantity){this.quantity=nQuantity;}
    public void setOrder_Id(String nOrderId){this.order_id=nOrderId;}
    public void setId(String nId){this.id=nId;}
    public void setRecipe(String nId){this.recipe=nId;}
    public Recipe getLine(){return this.line;}
    public int getQuantity(){return this.quantity;}
    public String getOrder_Id(){return this.order_id;}
    public String getId(){return this.id;}
    public String getRecipe(){return this.recipe;}

}
