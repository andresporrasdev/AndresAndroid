package algonquin.cst2335.porr0016;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
        ImageView profileImage = findViewById(R.id.profileImage);
        Button buttonChangePicture = findViewById(R.id.buttonChangePicture);

        //Welcome message
        textView.setText("Welcome back "+emailAddress);

        //Calling
        buttonCall.setOnClickListener(click ->{
            String phoneNumberCall = phoneNumber.getText().toString();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumberCall));
            startActivity(call);
        });


//        buttonChangePicture.setOnClickListener(click ->{
            //Taking a picture
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),

                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == Activity.RESULT_OK) {
                                Intent data = result.getData();
                                Bitmap thumbnail = null;
                                if (data != null) {
                                    thumbnail = data.getParcelableExtra("data");
                                }
                                profileImage.setImageBitmap(thumbnail);


                            }
                        }
                    });
            cameraResult.launch(cameraIntent);

//        });
    }
}