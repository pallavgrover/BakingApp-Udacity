package pallavgrover.bakingapp;


import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import pallavgrover.bakingapp.activity.RecipeListActivity;
import pallavgrover.bakingapp.activity.RecipieDetailsListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.android.exoplayer2.util.Assertions.checkNotNull;
import static org.hamcrest.Matchers.allOf;
import static pallavgrover.bakingapp.MainActivityRecyclerViewTest.RECIPE_NAME;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void recipeDetailActivityTest() {
//        ViewInteraction recyclerView = onView(
//                allOf(withId(R.id.recipie_recyler), isDisplayed()));
//        recyclerView.perform(actionOnItemAtPosition(0, click()));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Click on Recipe
        onView(withId(R.id.recipie_recyler))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(RECIPE_NAME)), click()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recipiedetails_list))
                .check(matches(atPosition(1, hasDescendant(withText("Graham Cracker crumbs")))));

        onView(withId(R.id.recipiedetails_list))
                .check(matches(atPosition(2, hasDescendant(withText("unsalted butter, melted")))));

        onView(withId(R.id.recipiedetails_list))
                .check(matches(atPosition(3, hasDescendant(withText("granulated sugar")))));

        onView(withId(R.id.recipiedetails_list))
                .check(matches(atPosition(4, hasDescendant(withText("salt")))));

        onView(withId(R.id.recipiedetails_list))
                .check(matches(atPosition(5, hasDescendant(withText("vanilla")))));

        onView(withId(R.id.recipiedetails_list))
                .check(matches(atPosition(6, hasDescendant(withText("Nutella or other chocolate-hazelnut spread")))));

        onView(withId(R.id.recipiedetails_list))
                .check(matches(atPosition(7, hasDescendant(withText("Mascapone Cheese(room temperature)")))));

        onView(withId(R.id.recipiedetails_list))
                .check(matches(atPosition(8, hasDescendant(withText("heavy cream(cold)")))));

        onView(withId(R.id.recipiedetails_list)).perform(RecyclerViewActions.scrollToPosition(9))
                .check(matches(atPosition(9, hasDescendant(withText("cream cheese(softened)")))));

        onView(withId(R.id.recipiedetails_list)).perform(RecyclerViewActions.scrollToPosition(11))
                .check(matches(atPosition(11, hasDescendant(withText("0. Recipe Introduction")))));

        onView(withId(R.id.recipiedetails_list)).perform(RecyclerViewActions.scrollToPosition(12))
                .check(matches(atPosition(12, hasDescendant(withText("1. Starting prep")))));

        onView(withId(R.id.recipiedetails_list)).perform(RecyclerViewActions.scrollToPosition(13))
                .check(matches(atPosition(13, hasDescendant(withText("2. Prep the cookie crust.")))));

        onView(withId(R.id.recipiedetails_list)).perform(RecyclerViewActions.scrollToPosition(14))
                .check(matches(atPosition(14, hasDescendant(withText("3. Press the crust into baking form.")))));

        onView(withId(R.id.recipiedetails_list)).perform(RecyclerViewActions.scrollToPosition(15))
                .check(matches(atPosition(15, hasDescendant(withText("4. Start filling prep")))));

        onView(withId(R.id.recipiedetails_list)).perform(RecyclerViewActions.scrollToPosition(16))
                .check(matches(atPosition(16, hasDescendant(withText("5. Finish filling prep")))));

        onView(withId(R.id.recipiedetails_list)).perform(RecyclerViewActions.scrollToPosition(17))
                .check(matches(atPosition(17, hasDescendant(withText("6. Finishing Steps")))));

    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}