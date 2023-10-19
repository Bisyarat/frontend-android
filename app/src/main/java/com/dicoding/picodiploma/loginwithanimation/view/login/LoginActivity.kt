package com.dicoding.picodiploma.loginwithanimation.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity
import java.util.regex.Pattern
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    var validateEmailText = false
    var validatePasswordText = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
//        setupAction()
        rulesEditText()
        validateWhenClickSubmitButtton()
    }

    private fun validateWhenClickSubmitButtton() {
        binding.loginButton.setOnClickListener {
            val emailText = binding.emailEditText.text.toString()
            val passwordText = binding.passwordEditText.text.toString()

            if (emailText.length === 0) {
                binding.emailEditTextLayout.error = getString(R.string.errorEmptyField)
            } else {
                binding.emailEditTextLayout.error = null
            }
            if (passwordText.length === 0) {
                binding.passwordEditTextLayout.error = getString(R.string.errorEmptyField)
            } else {
                binding.passwordEditTextLayout.error = null
            }

            if ((validateEmailText) && (validatePasswordText)) {
                successValidate()
            } else{
                Toast.makeText(this, "gagal", Toast.LENGTH_SHORT ).show()
            }


        }
    }

    private fun rulesEditText() {
        binding.emailEditText.doAfterTextChanged { text ->
            if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                binding.emailEditTextLayout.error = getString(R.string.errorEmail)
                validateEmailText = false
            } else {
                binding.emailEditTextLayout.error = null
                validateEmailText = true
            }
        }
        binding.passwordEditText.doOnTextChanged { text, start, before, count ->
            if (text!!.length < 8) {
                binding.passwordEditTextLayout.error = getString(R.string.error)
                validatePasswordText = false
            } else {
                binding.passwordEditTextLayout.error = null
                validatePasswordText = true
//                Toast.makeText(this, validatePasswordText.toString(), Toast.LENGTH_SHORT ).show()

            }
        }
    }


    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

//    private fun setupAction() {
//        binding.loginButton.setOnClickListener {
//            successValidate()
//        }
//    }

    private fun successValidate() {
        val email = binding.emailEditText.text.toString()
        viewModel.saveSession(UserModel(email, "sample_token"))
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Anda berhasil login. Sudah tidak sabar untuk belajar ya?")
            setPositiveButton("Lanjut") { _, _ ->
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

}