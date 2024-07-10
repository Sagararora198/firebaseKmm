package com.example.test.android

import AndroidPhoneVerificationProvider
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.test.Greeting
import com.google.firebase.FirebaseApp

import data.FirebaseRepository
import dev.gitlive.firebase.Firebase

import dev.gitlive.firebase.auth.AuthCredential
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.PhoneAuthProvider
import dev.gitlive.firebase.auth.PhoneVerificationProvider
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.initialize

class MainActivity : ComponentActivity() {
    private lateinit var firebaseRepository: FirebaseRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)

        firebaseRepository =
            FirebaseRepository(phoneAuthProvider = PhoneAuthProvider(Firebase.auth))
        val phoneVerificationProvider = AndroidPhoneVerificationProvider(this)


        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VerificationScreen(
                        onVerify = { phoneNumber ->
                            verifyPhoneNumber(phoneNumber, phoneVerificationProvider)
                        },
                        onSubmit = { verificationId, code ->

                        }

                    )
                }
            }
        }
    }
    @Composable
    fun VerificationScreen(onVerify: (String) -> Unit, onSubmit: (String, String) -> Unit) {
        var phoneNumber by remember { mutableStateOf("") }
        var verificationId by remember { mutableStateOf("") }
        var otpCode by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") }
            )
            Button(onClick = { onVerify(phoneNumber) }) {
                Text("Send OTP")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = otpCode,
                onValueChange = { otpCode = it },
                label = { Text("Enter OTP") }
            )
            Button(onClick = { onSubmit(verificationId, otpCode) }) {
                Text("Submit OTP")
            }
        }
    }




    private fun verifyPhoneNumber(phoneNumber: String, provider: PhoneVerificationProvider) {
        firebaseRepository.verifyNumber(phoneNumber, provider, ::onVerificationSuccess, ::onVerificationFailure)
    }
    private fun onVerificationSuccess(credential: AuthCredential) {
        Log.d("MainActivityPhone", "onVerificationSuccess: success")
    }

    private fun onVerificationFailure(exception: Exception) {
        // Handle verification failure
        Log.d("MainActivityPhone", "onVerificationSuccess: failure")
    }


}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
