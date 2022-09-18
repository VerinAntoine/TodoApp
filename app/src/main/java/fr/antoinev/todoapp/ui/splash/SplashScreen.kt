package fr.antoinev.todoapp.ui.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashScreen(
    navigate: (String) -> Unit,
    viewModel: SplashScreenViewModel = hiltViewModel(),
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = "Logo",
                modifier = Modifier.size(72.dp),
            )
            Spacer(Modifier.fillMaxHeight(.1f))
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(true) {
        viewModel.doRouting(navigate)
    }
}
