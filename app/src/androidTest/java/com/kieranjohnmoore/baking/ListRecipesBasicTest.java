package com.kieranjohnmoore.baking;

import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.kieranjohnmoore.baking.data.RetrofitBuilder;
import com.kieranjohnmoore.baking.ui.ActivityMain;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class)
public class ListRecipesBasicTest {
    @Rule
    public ActivityTestRule<ActivityMain> testRule = new ActivityTestRule<>(ActivityMain.class);
    private OkHttp3IdlingResource idlingResource;


    @Before
    public void setupRetrofit() {
        idlingResource = OkHttp3IdlingResource.create("OkHttp", RetrofitBuilder.getClient());

        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void ensureListOfRecipesAreDownloadedAndShown() {
        onView(withText(containsString("Nutella Pie"))).check(matches(isDisplayed()));
        onView(withText(containsString("Brownies"))).check(matches(isDisplayed()));
        onView(withText(containsString("Yellow Cake"))).check(matches(isDisplayed()));
    }

    @After
    public void clearIdleResources() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
