package algonquin.cst2335.porr0016.ui2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.porr0016.data.MainViewModel;
import algonquin.cst2335.porr0016.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
    private MainViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot()); //Loads XML on screen

       // setContentView(R.layout.activity_main);
        TextView myText = variableBinding.textview;
//        Button myButton = variableBinding.mybutton;
//        EditText myEdit = variableBinding.myedittext;
//        String editString = myEdit.getText().toString();
        model.editString.observe(this, s -> {
            variableBinding.textview.setText("Your edit text has:" +s);
        });

//        variableBinding.textview.setText((CharSequence) model.editString);
        variableBinding.mybutton.setOnClickListener(click -> {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
//            model.editString = variableBinding.myedittext.getText().toString();
//            variableBinding.textview.setText("Your edit text has: " + model.editString);
        });

//        myButton.setOnClickListener(v -> myText.setText("Your edit text has: " + model.editString));

//        myButton.setOnClickListener(v -> {
//            String editString = myEdit.getText().toString();
//            myText.setText( "Your edit text has: " + editString);
//        });
        model.isSelected.observe(this, isChecked -> {
            variableBinding.checkBox.setChecked(isChecked);
            variableBinding.radioButton.setChecked(isChecked);
            variableBinding.switch1.setChecked(isChecked);
            Toast.makeText(this, " The value is now: " + isChecked, Toast.LENGTH_LONG).show();
        });

        variableBinding.checkBox.setOnCheckedChangeListener((btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });

        variableBinding.switch1.setOnCheckedChangeListener((btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
        });
        variableBinding.radioButton.setOnCheckedChangeListener((btn,isChecked) -> {
            model.isSelected.postValue(isChecked);
        });

    }
}
