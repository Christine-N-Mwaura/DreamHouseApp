package com.christine.dreamhouseapp;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class loginTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ValidateUserNameInput() {
        onView(withId(R.id.userNameEditText)).perform(typeText("Christine")).check(matches(withText("Christine")));
    }

    @Test
    public void usernameIsSentToDashboard() {
        String name = "Winnie";
        onView(withId(R.id.userNameEditText)).perform(typeText(name),closeSoftKeyboard());

        try {
            Thread.sleep(250);
        }catch (InterruptedException ex){
            System.out.println("got interrupted!");
        }
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.contractorsTextView)).check(matches(withText("Welcome " + name + "."+ " Here are all the contractors we have : ")));
    }
}
