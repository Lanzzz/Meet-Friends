package projects.gabriel.calhacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Activity2 extends AppCompatActivity {

    //Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        //button = (Button)findViewById(R.id.correct_button); // weird
        //button.setOnClickListener(this);
    }
    /*
    public void buttonClick()
    {
        startActivity(new Intent("projects.gabriel.calhacks.Activity2)"));
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.correct_button:
            {
                buttonClick();
                break;
            }
        }
    }
    */

}
