import android.app.Activity
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken

import dev.gitlive.firebase.auth.PhoneVerificationProvider
import java.util.concurrent.TimeUnit

class AndroidPhoneVerificationProvider(
    override val activity: Activity,
    override val timeout:Long = 60,
    override val unit: TimeUnit = TimeUnit.SECONDS
) :PhoneVerificationProvider{

    private var storedVerificationId:String? = ""
    private lateinit var resendToken:ForceResendingToken



    override fun codeSent(triggerResend: (Unit) -> Unit) {
        TODO("Not yet implemented")
    }


    override suspend fun getVerificationCode(): String {
        // Show a UI to the user to enter the verification code and return the entered code
        // For demonstration, returning a dummy code
        return "123456"
    }



}