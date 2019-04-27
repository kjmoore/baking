package com.kieranjohnmoore.baking;

import android.content.Intent;

import com.google.gson.Gson;
import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.kieranjohnmoore.baking.data.RetrofitBuilder;
import com.kieranjohnmoore.baking.model.Recipe;
import com.kieranjohnmoore.baking.ui.ActivityMain;
import com.kieranjohnmoore.baking.ui.ActivityRecipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class)
public class ShowRecipeBasicTest {
    @Rule
    public ActivityTestRule<ActivityRecipe> testRule = new ActivityTestRule<>(ActivityRecipe.class, false, false);

    private String loadJson() {
        InputStream inputStream = getClass().getResourceAsStream("recipe.json");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            return "";
        }
    }

    private Recipe getRecipe() {
        return new Gson().fromJson(loadJson(), Recipe.class);
    }

    @Before
    public void setupActivity() {
        final Intent intent = new Intent();
        intent.putExtra(ActivityMain.DATA_RECIPE, getRecipe());
        testRule.launchActivity(intent);
    }

    @Test
    public void ensureStepsAreShown() {
        onView(withText(containsString("Ingredients"))).check(matches(isDisplayed()));
        onView(withText(containsString("Recipe Introduction"))).check(matches(isDisplayed()));
        onView(withText(containsString("Starting prep"))).check(matches(isDisplayed()));
        onView(withText(containsString("Prep the cookie crust"))).check(matches(isDisplayed()));
        onView(withText(containsString("Press the crust into baking form"))).check(matches(isDisplayed()));
    }

    @Test
    public void ensureIngredientsCanBeNavigatedTo() {
        onView(withId(R.id.ingredients)).perform(click());
        onView(withText(containsString("Graham Cracker crumbs"))).check(matches(isDisplayed()));
        onView(withText(containsString("unsalted butter"))).check(matches(isDisplayed()));
        onView(withText(containsString("granulated sugar"))).check(matches(isDisplayed()));
    }

    @Test
    public void ensureBackButtonReturnsFromIngredientsList() {
        onView(withId(R.id.ingredients)).perform(click());
        onView(withText(containsString("Graham Cracker crumbs"))).check(matches(isDisplayed()));
        pressBack();
        onView(withText(containsString("Ingredients"))).check(matches(isDisplayed()));
    }
}
