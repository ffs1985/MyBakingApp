package com.ffs1985.mybakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityInstrumentationTest {

    public static final String STEP_NAME = "Recipe Introduction";
    public static final String RECIPE_NAME = "Brownies";

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityRule
            = new ActivityTestRule<>(RecipeActivity.class);

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getInstrumentation()
                .getTargetContext();
        Intent intentToStartRecipeActivity = new Intent(context, RecipeActivity.class);
        intentToStartRecipeActivity.putExtra("recipe_id", 2);
        mActivityRule.launchActivity(intentToStartRecipeActivity);
        IdlingResource mIdlingResource = mActivityRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void testSelectStep() {


        Espresso.onView(ViewMatchers.withId(R.id.toolbar))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withText(RECIPE_NAME))
                .check(ViewAssertions.matches(ViewMatchers.withParent(ViewMatchers.withId(R.id.toolbar))));

        Espresso.onView(ViewMatchers.withId(R.id.recyclerview_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, ViewActions.click()));

    }
/*
    @Test
    public void testBackButton() {

    }
*/
}
