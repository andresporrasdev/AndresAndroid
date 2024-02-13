package algonquin.cst2335.porr0016;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Getting elements from previous activity
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");

        //Finding elements by id
        EditText phoneNumber = findViewById(R.id.editTextPhone);
        Button buttonCall = findViewById(R.id.buttonCall);
        TextView textView = findViewById(R.id.textView3);

        //Welcome message
        textView.setText("Welcome back "+emailAddress);

        //Calling
        buttonCall.setOnClickListener(click ->{
            String phoneNumberCall = phoneNumber.getText().toString();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumberCall));
            startActivity(call);
        });
    }
}