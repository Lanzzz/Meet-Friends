package projects.gabriel.calhacks;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /*final TextView firstTextView = (TextView) findViewById(R.id.textView);

        final Button firstButton = (Button) findViewById(R.id.button);

        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstTextView.setText("You Clicked");
            }
        });*/


    }

    public void buttonOnClick(View v) {

        final Button firstButton = (Button) findViewById(R.id.button);
        startActivity(new Intent(this, OptionSlide.class));
    }

}