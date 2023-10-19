package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.ResultState
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivitySignupBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginViewModel

class SignupActivity : AppCompatActivity() {
    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivitySignupBinding

    var validateNameText = false
    var validateEmailText = false
    var validatePasswordText = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        rulesEditText()
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

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            finalValidate()
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
            }
        }
    }

    private fun successValidate() {
        val email = binding.emailEditText.text.toString()
        val name = binding.nameEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        viewModel.register(UserModel(email, null, false, name, password)).observe(this) { result ->
            run {
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }
                        is ResultState.Success -> {
                            showToast(result.data.message)
                            showLoading(false)
                            alertBerhasil(email)
                        }

                        is ResultState.Error -> {
                            showToast(result.error)
                            showLoading(false)
                        }
                    }
                }
            }

        }

    }

    private fun finalValidate() {
        val nameText = binding.nameEditText.text.toString()
        val emailText = binding.emailEditText.text.toString()
        val passwordText = binding.passwordEditText.text.toString()

        if (nameText.length === 0) {
            binding.nameEditTextLayout.error = getString(R.string.errorEmptyField)
            validateNameText = false
        } else {
            binding.nameEditTextLayout.error = null
            validateNameText = true
        }

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

        if ((validateEmailText) && (validatePasswordText) && (validateNameText)) {
            successValidate()
        } else {
            Toast.makeText(this, "gagal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun alertBerhasil(email: String){
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
            setPositiveButton("Lanjut") { _, _ ->
                finish()
            }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}