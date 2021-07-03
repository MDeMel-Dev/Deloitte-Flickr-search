package com.example.deloitte_flickr_search.ui.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.deloitte_flickr_search.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest{

    @Test
    fun testNavigationToInGameScreen() {
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        // Create a graphical FragmentScenario for the TitleScreen
        val titleScenario = launchFragmentInContainer<MainFragment>()

        titleScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.mobile_navigation)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Verify that performing a click changes the NavController’s state
        onView(ViewMatchers.withId(R.id.action_info)).perform(ViewActions.click())
        org.hamcrest.MatcherAssert.assertThat("home to info navigation"  ,navController.currentDestination?.id!!.equals(R.id.navigation_info))
    }

}