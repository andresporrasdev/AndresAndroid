package algonquin.cst2335.porr0016;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variable declaration
        TextView tv = findViewById(R.id.textView);
        EditText et = findViewById(R.id.editTextTextPassword);
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(clk ->{
            String password = et.getText().toString();
            checkPasswordComplexity(password);
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