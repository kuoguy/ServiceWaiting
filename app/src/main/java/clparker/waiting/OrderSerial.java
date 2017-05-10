package clparker.waiting;

import android.os.Parcel;
import android.os.Parcelable;

import static android.content.ComponentName.readFromParcel;

/**
 * Created by Clown on 10/05/2017.
 */

public class OrderSerial implements Parcelable {

    private String order_id;
    private int table;
    private String[] recipes;

    public void writeToParcel(Parcel p, int i)
    {

    }

    public static final Parcelable.Creator<OrderSerial> CREATOR
            = new Parcelable.Creator<OrderSerial>() {
        public OrderSerial createFromParcel(Parcel in) {
            return new OrderSerial(in);
        }

        public OrderSerial[] newArray(int size) {
            return new OrderSerial[size];
        }
    };

    public int describeContents()
    {
        return 0;
    }

    public OrderSerial(Parcel in)
    {
        readFromParcel(in);
    }
    public OrderSerial()
    {

    }
}
