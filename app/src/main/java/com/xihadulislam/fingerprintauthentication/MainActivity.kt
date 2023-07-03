package com.xihadulislam.fingerprintauthentication

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import com.xihadulislam.fingerprintauthentication.databinding.ActivityMainBinding
import com.xihadulislam.fingerprintauthentication.ext.BiometricPromptUtils
import com.xihadulislam.fingerprintauthentication.ext.Const

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var biometricPrompt: BiometricPrompt? = null
    private var promptInfo: BiometricPrompt.PromptInfo? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBiometricPrompt()

        binding.btnAuth.setOnClickListener {
            showBiometricPrompt()
        }

    }

    private fun initBiometricPrompt() {
        biometricPrompt = BiometricPromptUtils.createBiometricPrompt(this, ::processResult)
        promptInfo = BiometricPromptUtils.createPromptInfo(this)
    }

    private fun showBiometricPrompt() {
        promptInfo?.let {
            biometricPrompt?.authenticate(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun processResult(
        status: String, result: BiometricPrompt.AuthenticationResult?, msg: String
    ) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        when (status) {
            Const.SUCCESS -> {
                binding.txtTitle.text = "Auth Success"
                binding.FingerPrintImage.setColorFilter(Color.BLUE)
            }

            Const.FAILED -> {
                binding.txtTitle.text = "Auth Failed"
                binding.FingerPrintImage.setColorFilter(Color.RED)
            }

            Const.ERROR -> {
                binding.txtTitle.text = "Auth Error"
                binding.FingerPrintImage.setColorFilter(Color.RED)

            }
        }

    }

}