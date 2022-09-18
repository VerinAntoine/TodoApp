package fr.antoinev.todoapp.ui.login

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.antoinev.todoapp.extensions.getActivity
import fr.antoinev.todoapp.services.AuthService

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(
    navigate: (String) -> Unit,
    viewModel: LoginScreenViewModel = hiltViewModel(),
    activity: Activity = LocalContext.current.getActivity()!!
) {
    val state by viewModel.state

    when (state.currentStep) {
        LoginScreenStep.PHONE_NUMBER -> PhoneNumberForm(
            value = state.phoneNumber,
            onValueChange = { viewModel.onPhoneNumberChange(it) },
            error = viewModel.error.value,
            onSent = { viewModel.onPhoneNumberSent(activity, navigate) }
        )
        LoginScreenStep.PHONE_NUMBER_VERIFICATION -> PhoneNumberLoading()
        LoginScreenStep.VERIFICATION_CODE -> VerificationCodeForm(
            value = state.verificationCode,
            onValueChange = { viewModel.onVerificationCodeChange(it) },
            error = viewModel.error.value,
            onSent = { viewModel.onVerificationCodeSent(navigate) }
        )
    }
}

@ExperimentalMaterial3Api
@Composable
private fun PhoneNumberForm(
    value: String,
    onValueChange: (String) -> Unit,
    error: AuthService.AuthError?,
    onSent: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = "Logo",
                    modifier = Modifier.size(72.dp)
                )
                Spacer(modifier = Modifier.width(13.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Welcome on TodoApp", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Please sign in", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(Modifier.height(60.dp))
            if(error != null) ErrorText(error)
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(text = "Phone number") },
                placeholder = { Text(text = "+33 6 00 00 00 00") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions { onSent() },
                singleLine = true,
                visualTransformation = PhoneNumberVisualTransformation(),
                isError = error != null,
            )

            Spacer(Modifier.height(60.dp))
            Button(onClick = onSent) {
                Text(text = "Send me a code", style = MaterialTheme.typography.labelLarge)
            }
        }

    }
}

@Composable
private fun PhoneNumberLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "We're verifying your phone number")
            Spacer(modifier = Modifier.height(24.dp))
            CircularProgressIndicator()
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun VerificationCodeForm(
    value: String,
    onValueChange: (String) -> Unit,
    error: AuthService.AuthError?,
    onSent: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Enter your verification code", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(32.dp))
            if(error != null) ErrorText(error)
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(text = "Code") },
                placeholder = { Text(text = "123 456") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions { onSent() }
            )
            Spacer(modifier = Modifier.height(60.dp))
            Button(onClick = onSent) {
                Text(text = "Sign me in")
            }
        }
    }
}

@Composable
private fun ErrorText(error: AuthService.AuthError) {
    val errorMessage = when(error) {
        AuthService.AuthError.INVALID_CREDENTIALS -> "Your phone number is not valid"
        AuthService.AuthError.OUT_OF_QUOTA -> "Your phone number has been blocked du tu unusual activity"
        AuthService.AuthError.INVALID_VERIFICATION_CODE -> "Invalid verification code"
        AuthService.AuthError.UNKNOWN -> "An error occurs, the developer haven't done is job"
    }

    Text(
        text = errorMessage,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.labelMedium
    )
}
