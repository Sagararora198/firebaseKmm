package data

import dev.gitlive.firebase.auth.PhoneAuthProvider

object FirebaseRepoProvider {
    val repository:FirebaseRepository by lazy {
        FirebaseRepository(PhoneAuthProvider())
    }
}