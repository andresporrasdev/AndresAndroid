package algonquin.cst2335.porr0016;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 *
 * @author Andres Porras(041056717)
 * @version 1.0
 *
 * MainActivity serves as the app's main interface, where users can enter a password and submit it for complexity validation. It extends {@link AppCompatActivity} and manages UI components like {@link EditText} for input and {@link Button} for initiating the validation process, providing feedback on password requirements.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Initializes the activity, its UI components, and sets up the button click listener to trigger
     * password complexity validation. It binds the UI components to their respective views in the layout
     * and defines the action to be taken when the user clicks the submit button.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variable declaration
        /**
         * TextView to display feedback on password complexity validation. It shows success or failure messages
         * based on the user's password input.
         */
        TextView tv = findViewById(R.id.textView);

        /**
         * EditText where users input their password. This field captures the user's password input, which
         * is then validated for complexity upon submission.
         */
        EditText et = findViewById(R.id.editTextTextPassword);
        /**
         * Button that users click to submit their password for complexity validation. It triggers the
         * password complexity check when clicked.
         */
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(clk ->{
            String password = et.getText().toString();
            if (checkPasswordComplexity(password)) {
                tv.setText("Your password meets the requirements");
            } else {
                tv.setText("You shall not pass!");
            }
        });
    }

    /**
     * This function should check if this string has an Upper Case letter,
     * a lower case letter, a number, and a special symbol.
     *
     * @param pw The String object that we are checking
     * @return Returns true if the String meets the requirements
     */
    private boolean checkPasswordComplexity(String pw) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        String specialCharacters = "#$%^&*!@?";

        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);

            if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (Character.isDigit(c)) {
                foundNumber = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }
        }

        if (!foundUpperCase) {
            Toast.makeText(MainActivity.this, "You are missing an upper case letter.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(MainActivity.this, "You are missing a lower case letter.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(MainActivity.this, "You are missing a number.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(MainActivity.this, "You are missing a special character (#$%^&*!@?).", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * This function should check if this character is a special symbol.
     *
     * @param c The character we are checking
     * @return true if c is special symbol
     */
    boolean isSpecialCharacter(char c) {
        switch (c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }
}