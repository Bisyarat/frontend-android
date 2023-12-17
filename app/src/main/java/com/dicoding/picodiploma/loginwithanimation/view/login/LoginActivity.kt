package com.dicoding.picodiploma.loginwithanimation.view.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        rulesEditText()
        validateWhenClickSubmitButtton()
    }

    private fun validateWhenClickSubmitButtton() {
        binding.loginButton.setOnClickListener {
            val emailText = binding.emailEditText.text.toString()
            val passwordText = binding.passwordEditText.text.toString()
            val statePasswordText = if (passwordText.length < 8) false else true

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

            if (!statePasswordText){
                binding.passwordEditTextLayout.error = getString(R.string.error)
            } else{
                binding.passwordEditTextLayout.error = null
            }

            if ((validateEmailText) && (statePasswordText)) {
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

    private fun successValidate() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        checkLoginFromApi(email, password)
    }

    private fun alertBerhasil(){
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

    private fun checkLoginFromApi(email: String, password: String) : Boolean{
        var dataAvailable: Boolean = false
        viewModel.login(UserModel("","",email,"",false,password)).observe(this) { result ->
            run {
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                            dataAvailable = false
                        }
                        is ResultState.Success -> {
                            val message = result.data.message!!
                            val token = result.data.loginResult.token

                            showToast(message)
                            showLoading(false)
                            viewModel.saveSession(UserModel("","",email,"",false,password))
                            alertBerhasil()
                            dataAvailable = true
                        }

                        is ResultState.Error -> {
                            showToast(result.error)
                            showLoading(false)
                            dataAvailable = false
                        }
                    }
                }
            }

        }

        return dataAvailable
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}