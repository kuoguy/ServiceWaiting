package clparker.waiting;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

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

    public void sendMessage(View view) {
        switch (getResources().getResourceEntryName(view.getId())) {
            case "imageButtonNewOrder":
                try
                {
                    ImageButton button = (ImageButton) findViewById(R.id.imageButtonNewOrder);
                    button.setImageResource(R.drawable.orderbuttonpressed);
                    Intent intent = new Intent(getActivity(), CreateOrder.class);
                    startActivity(intent);
                } catch (NullPointerException e) {
                    Log.d("Menu", "Button not working:" + e.getMessage());
                }
                break;
            case "imageButtonModifyOrder":
                try
                {
                    ImageButton button = (ImageButton) findViewById(R.id.imageButtonModifyOrder);
                    button.setImageResource(R.drawable.modifybuttonpressed);
                    Intent intent = new Intent(getActivity(), ModifyOrder.class);
                    startActivity(intent);
                } catch (NullPointerException e) {
                    Log.d("Menu", "Button not working:" + e.getMessage());
                }
                break;
        }
    }

    public Activity getActivity()
    {
        return this;
    }


}
