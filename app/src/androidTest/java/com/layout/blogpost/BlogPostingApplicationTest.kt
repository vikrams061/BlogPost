package com.layout.blogpost

import android.view.WindowManager
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.anything
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.platform.app.InstrumentationRegistry
import android.provider.Settings;
import androidx.test.uiautomator.UiDevice
import org.junit.After

@RunWith(AndroidJUnit4::class)
class BlogPostingApplicationTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        // Disable animations
        val disableAnimationFlag = 0
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val animatorScale = Settings.Global.getFloat(instrumentation.targetContext.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f)
        val transitionScale = Settings.Global.getFloat(instrumentation.targetContext.contentResolver, Settings.Global.TRANSITION_ANIMATION_SCALE, 1f)
        val windowScale = Settings.Global.getFloat(instrumentation.targetContext.contentResolver, Settings.Global.WINDOW_ANIMATION_SCALE, 1f)
        if (animatorScale > 0 || transitionScale > 0 || windowScale > 0) {
            UiDevice.getInstance(instrumentation).executeShellCommand("settings put global window_animation_scale $disableAnimationFlag")
            UiDevice.getInstance(instrumentation).executeShellCommand("settings put global transition_animation_scale $disableAnimationFlag")
            UiDevice.getInstance(instrumentation).executeShellCommand("settings put global animator_duration_scale $disableAnimationFlag")
        }
    }

    @After
    fun tearDown() {
        // Re-enable animations
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val animatorScale = Settings.Global.getFloat(instrumentation.targetContext.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f)
        val transitionScale = Settings.Global.getFloat(instrumentation.targetContext.contentResolver, Settings.Global.TRANSITION_ANIMATION_SCALE, 1f)
        val windowScale = Settings.Global.getFloat(instrumentation.targetContext.contentResolver, Settings.Global.WINDOW_ANIMATION_SCALE, 1f)
        if (animatorScale == 0f || transitionScale == 0f || windowScale == 0f) {
            UiDevice.getInstance(instrumentation).executeShellCommand("settings put global window_animation_scale $windowScale")
            UiDevice.getInstance(instrumentation).executeShellCommand("settings put global transition_animation_scale $transitionScale")
            UiDevice.getInstance(instrumentation).executeShellCommand("settings put global animator_duration_scale $animatorScale")
        }
    }

    @Test
    fun testCreatePostButton() {
        // Click on the "Post" button
        onView(withId(R.id.post_button)).perform(click())

        // Check if the "Create Post" screen is opened
        onView(withId(R.id.title_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.description_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.submit_button)).check(matches(isDisplayed()))
    }

    @Test
    fun testCreatePost() {
        // Click on the "Post" button
        onView(withId(R.id.post_button)).perform(click())

        // Enter post title and description
        val title = "Test Post"
        val desc = "This is a test post."
        onView(withId(R.id.title_edit_text)).perform(typeText(title))
        onView(withId(R.id.description_edit_text)).perform(typeText(desc))
        closeSoftKeyboard()
        // Click on the "Submit" button
        onView(withId(R.id.submit_button)).perform(click())

        // Check if the post is displayed in the "View Posts" screen
        onView(withText(title)).check(matches(isDisplayed()))
        onView(withText(desc)).check(matches(isDisplayed()))
    }

    @Test
    fun testClickPost() {
        onView(withId(R.id.view_button)).perform(click())

        // Wait for the ListView to be displayed
        onView(withId(R.id.list_view)).check(matches(isDisplayed()))

        // Click on the first item in the ListView
        onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(0).perform(click())

        onView(withId(R.id.title_edit_text)).perform(typeText("Hi All"))
        onView(withId(R.id.description_edit_text)).perform(typeText("Test Post"))
    }
}
