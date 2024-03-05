package algonquin.cst2335.porr0016;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText emailEditText = findViewById(R.id.editTextTextEmailAddress);


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
        EditText emailEditText;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String TAG = "MainActivity";
        Button loginButton = findViewById(R.id.loginButton);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);


        //SharedPreferences.
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
//        prefs.getString("VariableName", "");
        String emailAddress = prefs.getString("LoginName", "");

        Log.d( TAG, "Message");
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
        Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);
        loginButton.setOnClickListener(clk->{
            String emailAddress2 = emailEditText.getText().toString();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", emailAddress2);
            editor.apply();
            nextPage.putExtra( "EmailAddress", emailEditText.getText().toString() );
            startActivity( nextPage);});




    }
}