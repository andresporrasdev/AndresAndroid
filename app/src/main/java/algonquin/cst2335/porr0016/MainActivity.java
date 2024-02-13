package algonquin.cst2335.porr0016;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        Log.w( "MainActivity", "In onDestroy() - Any memory used by the application is freed" );
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        Log.w( "MainActivity", "In onStop() - The application is not longer visible" );
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.w( "MainActivity", "In onPause() - The application not longer respond to user input" );
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.w( "MainActivity", "In onResume() - The application is now responding to user input" );
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.w( "MainActivity", "In onStart() - The application is now visible on screen" );
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String TAG = "MainActivity";
        Button loginButton = findViewById(R.id.loginButton);

        Log.d( TAG, "Message");
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
        loginButton.setOnClickListener(clk->{        Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);
            startActivity( nextPage);});




    }
}