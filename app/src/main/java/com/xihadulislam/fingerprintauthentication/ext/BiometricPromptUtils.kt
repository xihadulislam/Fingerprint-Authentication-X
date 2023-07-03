package com.xihadulislam.fingerprintauthentication.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.xihadulislam.fingerprintauthentication.R
import com.xihadulislam.fingerprintauthentication.ext.Const.ERROR
import com.xihadulislam.fingerprintauthentication.ext.Const.FAILED
import com.xihadulislam.fingerprintauthentication.ext.Const.SUCCESS

object BiometricPromptUtils {
    private const val TAG = "BiometricPromptUtils"

    fun createPromptInfo(activity: AppCompatActivity): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle(activity.getString(R.string.prompt_info_title))
            setSubtitle(activity.getString(R.string.prompt_info_subtitle))
            setDescription(activity.getString(R.string.prompt_info_description))
            setConfirmationRequired(false)
            setNegativeButtonText(activity.getString(R.string.prompt_info_use_app_password))
        }.build()

    fun createBiometricPrompt(
        activity: AppCompatActivity,
        processResult: (status: String, BiometricPrompt.AuthenticationResult?, msg: String) -> Unit
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)

        val callback = object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errCode, errString)
                processResult(ERROR, null, "errCode is $errCode and errString is: $errString")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                processResult(FAILED, null, "Biometric authentication failed for unknown reason.")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                processResult(SUCCESS, result, "Biometric authentication was successful")
            }
        }
        return BiometricPrompt(activity, executor, callback)
    }

}