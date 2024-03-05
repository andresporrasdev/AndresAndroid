package algonquin.cst2335.porr0016;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    EditText phoneNumber = findViewById(R.id.editTextPhone);

    private ActivityResultLauncher<Intent> cameraResultLauncher;

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Finding elements by id
        Button buttonCall = findViewById(R.id.buttonCall);
//        TextView textView = findViewById(R.id.textView3);
        ImageView profileImage = findViewById(R.id.profileImage);
        Button buttonChangePicture = findViewById(R.id.buttonChangePicture);

        //Welcome message
//        textView.setText("Welcome back "+emailAddress);

        // Check if picture file exists
        File file = new File(getFilesDir(), "Picture.png");
        if (file.exists()) {
            // Load the image into a Bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            // Set the Bitmap to the ImageView
            profileImage.setImageBitmap(bitmap);
        }

        //Calling
        buttonCall.setOnClickListener(click ->{
            String phoneNumberCall = phoneNumber.getText().toString();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumberCall));
            startActivity(call);
        });

        cameraResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            if (thumbnail != null) {
                                profileImage.setImageBitmap(thumbnail);

                                FileOutputStream fOut = null;
                                try {
                                    fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                    fOut.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    if (fOut != null) {
                                        try {
                                            fOut.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                });

//        cameraResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == RESULT_OK) {
//                        Intent data = result.getData();
//                        assert data != null;
//                        Bitmap thumbnail = data.getParcelableExtra("data");
//                        profileImage.setImageBitmap(thumbnail);
//                    }
//                });

        buttonChangePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraResultLauncher.launch(cameraIntent);
            }
        });
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String savedPhoneNumber = prefs.getString("PhoneNumber", "");
        phoneNumber.setText(savedPhoneNumber);

        //Getting elements from previous activity
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");


        TextView welcomeText = findViewById(R.id.textView3);

        welcomeText.setText("Welcome Back  " + emailAddress);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        String phoneNumber2 = phoneNumber.getText().toString();
        SharedPreferences.Editor editor = getSharedPreferences("MyData", Context.MODE_PRIVATE).edit();
        editor.putString("PhoneNumber", phoneNumber2);
        editor.apply();
    }
}
