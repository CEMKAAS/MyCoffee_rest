package ru.zaroslikov.mycoffee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ru.zaroslikov.mycoffee.ui.theme.MyCoffeeApp
import ru.zaroslikov.mycoffee.ui.theme.screens.FirstScreen
import ru.zaroslikov.mycoffee.ui.theme.MyCoffeeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCoffeeTheme {
                MyCoffeeApp()
            }
        }
    }
}

