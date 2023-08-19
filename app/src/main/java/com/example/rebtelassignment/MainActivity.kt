package com.example.rebtelassignment


import com.example.rebtelassignment.view.CountryInfoScreen
import com.example.rebtelassignment.viewmodel.CountryInfoViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.rebtelassignment.ui.theme.RebtelAssignmentTheme
import com.example.rebtelassignment.utils.Utils.Companion.isNetworkAvailable
import com.example.rebtelassignment.view.ConnectionLostScreen

/**
 * Main entry point of the application.
 *
 * This class represents the main activity of the app and is responsible for setting up the UI
 * using Jetpack Compose. It initializes the ViewModel and displays the `com.example.rebtelassignment.view.CountryInfoScreen`.
 */
class MainActivity : ComponentActivity() {
    private val viewModel: CountryInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RebtelAssignmentTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isNetworkAvailable(this)) {
                        CountryInfoScreen(viewModel = viewModel)
                    } else {
                        ConnectionLostScreen(R.drawable.connectionlost)
                    }
                }
            }
        }
    }
}
