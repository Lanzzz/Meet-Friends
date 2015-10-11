package projects.gabriel.calhacks;

import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class OptionSlide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_slide);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

        public void buttonClick1(View v) // add Contact link
        {
           final Button addButton = (Button) findViewById(R.id.addContact);
           startActivity(new Intent(this, OptionSlide.class));
        }

        public void buttonClick2(View v) // view map link
        {
                final Button mapButton = (Button) findViewById(R.id.map);
                startActivity(new Intent(this, map.class));
        }

        public void buttonClick3(View v) // view friend list
        {
                final Button friendButton = (Button) findViewById(R.id.viewFriends);
                startActivity(new Intent(this, OptionSlide.class));
        }

        public void buttonClick4(View v) // view Hackathon Credits
        {
                final Button creditsButton = (Button) findViewById(R.id.team);
                startActivity(new Intent(this, HackathonCredits.class));
        }

}
