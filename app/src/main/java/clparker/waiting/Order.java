package clparker.waiting;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by Clown on 02/01/2017.
 */

public class Order {

    private String id;
    private int location;
    private int table;
    private ArrayList<Order_Line> lines=new ArrayList<>();
    //private String
    private Calendar created;
    private String createdTime;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    private String order_id;

    public void setId(String nId){id=nId;}
    public void setLocation(int nLocation){location=nLocation;}
    public void setTable(int nTable){table=nTable;}
    public String getId(){return id;}
    public int getLocation(){return location;}
    public int getTable(){return table;}
    public Calendar getCreated(){return created;}
    public String getCreatedTime(){return createdTime;}
    public int getSize(){return lines.size();}

    public void addLine(Order_Line nOrderLine){lines.add(nOrderLine);}
    public Order_Line getLine(int pos){return lines.get(pos);}
    public ArrayList<Order_Line> getLines(){return lines;}
    public void setLines(List<Order_Line> nLines)
    {
        lines.clear();
        for(int count=0; count<nLines.size(); count++)
        {
            lines.add(nLines.get(count));
        }
    }
    public void setCreated(Calendar nCreated){created=nCreated;}
    public void setCreatedTime(String nCreatedTime){createdTime=nCreatedTime;}

    public Order(int nLocation, int nTable)
    {
        this.setLocation(nLocation);
        this.setTable(nTable);
        this.setCreatedTime(Calendar.getInstance().getTime().toString());
        String uuid = UUID.randomUUID().toString();
        this.order_id=uuid;
    }


}
