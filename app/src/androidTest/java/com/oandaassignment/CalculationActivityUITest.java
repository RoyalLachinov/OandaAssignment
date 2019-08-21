package com.oandaassignment;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.oandaassignment.view.CalculateActivity;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class CalculationActivityUITest {

    @Rule
    public ActivityTestRule<CalculateActivity> calculateActivityActivityTestRule = new ActivityTestRule<>(CalculateActivity.class);
    private CalculateActivity calculateActivity = null;

    @Before
    public void setUp() throws Exception {
        calculateActivity = calculateActivityActivityTestRule.getActivity();
    }

    @Test
    public void checkToolbarTitle() {

        // Checking if app name equals to toolBar name
        CharSequence toolBarTitle = InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.app_name);
        Espresso.onView(Matchers.allOf(Matchers.instanceOf(TextView.class), ViewMatchers.withParent(ViewMatchers.isAssignableFrom(Toolbar.class))))
                .check(ViewAssertions.matches(ViewMatchers.withText(toolBarTitle.toString())));
    }


    @Test
    public void selectSourceSpinnerPosition() throws Exception {
        Espresso.onView(ViewMatchers.withId(R.id.spinnerSource)).perform(ViewActions.click());
        Espresso.onData(Matchers.allOf(Matchers.is(Matchers.instanceOf(String.class)))).atPosition(0).perform(ViewActions.click());
    }

    @Test
    public void selectSourceSpinnerText() throws Exception {
        Espresso.onView(ViewMatchers.withId(R.id.spinnerSource)).perform(ViewActions.click());
        Espresso.onData(Matchers.allOf(Matchers.is(Matchers.instanceOf(String.class)), Matchers.is("CAD"))).perform(ViewActions.click());
    }

    @Test
    public void testViewNullability() {

        // Checking if all views are exist
        EditText editTextSource = calculateActivity.findViewById(R.id.editTextSource);
        EditText editTextDestination = calculateActivity.findViewById(R.id.editTextDestination);
        Spinner spinnerSource = calculateActivity.findViewById(R.id.spinnerSource);
        Spinner spinnerDestination = calculateActivity.findViewById(R.id.spinnerDestination);

        Assert.assertNotNull(editTextSource);
        Assert.assertNotNull(editTextDestination);
        Assert.assertNotNull(spinnerSource);
        Assert.assertNotNull(spinnerDestination);
    }

    @Test
    public void testViewVisibility() {

        //Checking if all views are visible
        Espresso.onView(ViewMatchers.withId(R.id.editTextSource)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextDestination)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.spinnerSource)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.spinnerDestination)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testViewIsEnable() {

        //Checking if all views are enabled

        Espresso.onView(ViewMatchers.withId(R.id.editTextSource)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withId(R.id.editTextDestination)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withId(R.id.spinnerSource)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        Espresso.onView(ViewMatchers.withId(R.id.spinnerDestination)).check(ViewAssertions.matches(ViewMatchers.isEnabled()));

    }

    @Test
    public void testSourceEditTextFocus() {

        // Check is sourceEditText is focused
        Espresso.onView(ViewMatchers.withId(R.id.editTextSource)).check(ViewAssertions.matches(ViewMatchers.hasFocus()));
        Espresso.onView(ViewMatchers.withId(R.id.spinnerSource)).check(ViewAssertions.matches(ViewMatchers.isFocusable()));

    }


    @Test
    public void checkSourceEditTextValueWithNumbers() {
        // TextChange
        Espresso.onView(ViewMatchers.withId(R.id.editTextSource))
                .perform(ViewActions.typeText("23312?++__:,><."), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(IsNot.not(Double.NaN)));

        Espresso.onView(ViewMatchers.withId(R.id.editTextSource))
                .perform(ViewActions.clearText(), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(IsNot.not(Double.NaN)));
    }

    @Test
    public void checkSourceEditTextValueWithCharacters() {
        // TextChange
        Espresso.onView(ViewMatchers.withId(R.id.editTextSource))
                .perform(ViewActions.typeText("fgfgfg??++__:,><."), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(IsNot.not(Double.NaN)));

        Espresso.onView(ViewMatchers.withId(R.id.editTextSource))
                .perform(ViewActions.clearText(), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(IsNot.not(Double.NaN)));
    }

    @Test
    public void checkDestinationEditTextValueWithNumbers() {
        // TextChange
        Espresso.onView(ViewMatchers.withId(R.id.editTextDestination))
                .perform(ViewActions.typeText("23312?++__:,><."), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(IsNot.not(Double.NaN)));

        Espresso.onView(ViewMatchers.withId(R.id.editTextDestination))
                .perform(ViewActions.clearText(), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(IsNot.not(Double.NaN)));
    }

    @Test
    public void checkDestinationEditTextValueWithCharacters() {
        // TextChange
        Espresso.onView(ViewMatchers.withId(R.id.editTextDestination))
                .perform(ViewActions.typeText("fgfgfg??++__:,><."), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(IsNot.not(Double.NaN)));

        Espresso.onView(ViewMatchers.withId(R.id.editTextDestination))
                .perform(ViewActions.clearText(), ViewActions.closeSoftKeyboard())
                .check(ViewAssertions.matches(IsNot.not(Double.NaN)));

    }

    @After
    public void finishTest() {
        calculateActivity = null;
    }

    private boolean isValueDouble(String editTextValue) {
        String regexString = "^[0-9]*$";

        if (editTextValue.trim().matches(regexString)) {
            return true;
        } else {
            return false;
        }
    }
}
