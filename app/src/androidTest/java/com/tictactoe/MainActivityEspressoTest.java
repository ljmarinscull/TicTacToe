package com.tictactoe;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureWinnerScoreWork() {
        onView(withId(R.id.b11)).perform(click());
        onView(withId(R.id.b12)).perform(click());

        onView(withId(R.id.b21)).perform(click());
        onView(withId(R.id.b22)).perform(click());

        onView(withId(R.id.b31)).perform(click());

        onView(withText(R.string.toast_p1_wins)).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        onView(withId(R.id.p1_wins_value)).check(matches(withText("1")));
        onView(withId(R.id.reset)).perform(click());
        onView(withId(R.id.p1_wins_value)).check(matches(withText("0")));
    }
}
