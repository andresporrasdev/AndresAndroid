package algonquin.cst2335.porr0016;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for {@link MainActivity} using Espresso.
 * This class demonstrates how to interact with a UI for testing purposes,
 * including entering text, clicking buttons, and verifying UI responses.
 *
 * @LargeTest annotation indicates that this is a large integration test that can interact with views.
 * @RunWith(AndroidJUnit4.class) specifies that this test will be run with the Android JUnit 4 test runner.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    /**
     * Rule that launches the activity to be tested. This provides a convenient way to start the
     * activity before each test method and shut it down after each test method.
     */
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Tests the MainActivity's behavior when an invalid password is entered.
     * It enters a simple password, clicks the login button, and checks if the expected error message is displayed.
     */
    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView( withId(R.id.editTextTextPassword));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView( withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView) );
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests the MainActivity's behavior for detecting a missing uppercase letter in the password.
     * It inputs a password lacking an uppercase letter, clicks the login button, and verifies if the correct feedback is shown.
     */
    @Test
    public void testFindMissingUpperCase() {
        ViewInteraction appCompatEditText = onView( withId(R.id.editTextTextPassword));
        appCompatEditText.perform(replaceText("password123#$*"));

        ViewInteraction materialButton = onView( withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView) );
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests the MainActivity's behavior for detecting a missing lowercase letter in the password.
     * It inputs a password lacking a lowercase letter, clicks the login button, and verifies if the correct feedback is shown,
     * indicating that the password does not meet the complexity requirements due to the missing lowercase character.
     */
    @Test
    public void testFindMissingLowerCase() {
        ViewInteraction appCompatEditText = onView( withId(R.id.editTextTextPassword));
        appCompatEditText.perform(replaceText("PWS123#$*"));

        ViewInteraction materialButton = onView( withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView) );
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests the MainActivity's behavior for detecting a missing special character in the password.
     * It inputs a password lacking a special character, clicks the login button, and checks if the expected feedback is displayed,
     * indicating that the password does not meet the complexity requirements due to the absence of a special character.
     */
    @Test
    public void testFindMissingSpecial() {
        ViewInteraction appCompatEditText = onView( withId(R.id.editTextTextPassword));
        appCompatEditText.perform(replaceText("Password123"));

        ViewInteraction materialButton = onView( withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView) );
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests the MainActivity's behavior for detecting a missing digit in the password.
     * It inputs a password without any digits, clicks the login button, and verifies if the expected feedback message is shown,
     * indicating that the password does not meet the complexity requirements because it lacks a numeric character.
     */
    @Test
    public void testFindMissingDigit() {
        ViewInteraction appCompatEditText = onView( withId(R.id.editTextTextPassword));
        appCompatEditText.perform(replaceText("Password@@@"));

        ViewInteraction materialButton = onView( withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView) );
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Tests the MainActivity's behavior when the input password meets all the complexity requirements.
     * It inputs a valid password containing uppercase and lowercase letters, digits, and special characters, clicks the login button,
     * and checks if the feedback indicates that the password meets the requirements.
     */
    @Test
    public void testMeetAll() {
        ViewInteraction appCompatEditText = onView( withId(R.id.editTextTextPassword));
        appCompatEditText.perform(replaceText("Password123@@@"));

        ViewInteraction materialButton = onView( withId(R.id.button));
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView) );
        textView.check(matches(withText("Your password meets the requirements")));
    }

    /**
     * A helper method to create a matcher that finds a child View at a specific position within its parent.
     * This is useful for matching views that are part of a list or grid.
     *
     * @param parentMatcher The matcher for the parent view.
     * @param position The position of the child view in the parent.
     * @return A Matcher that matches a child view located in the specified position within its parent.
     */
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
