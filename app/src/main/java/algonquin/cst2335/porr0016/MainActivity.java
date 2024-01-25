package algonquin.cst2335.porr0016;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2335.porr0016.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot()); //Loads XML on screen

       // setContentView(R.layout.activity_main);
        TextView myText = variableBinding.textview;
        Button myButton = variableBinding.mybutton;
        EditText myEdit = variableBinding.myedittext;

        myButton.setOnClickListener(v -> {
            String editString = myEdit.getText().toString();
            myText.setText( "Your edit text has: " + editString);
        });

    }
}
