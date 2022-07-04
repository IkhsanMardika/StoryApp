package com.karyadika.storyapp.ui.user.login

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.karyadika.storyapp.R
import com.karyadika.storyapp.data.remote.user.login.LoginRequest
import com.karyadika.storyapp.data.remote.user.login.LoginResult
import com.karyadika.storyapp.databinding.ActivityLoginBinding
import com.karyadika.storyapp.ui.main.MainActivity
import com.karyadika.storyapp.ui.user.register.RegisterActivity
import com.karyadika.storyapp.utils.Result
import com.karyadika.storyapp.utils.UserViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels {
        UserViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupListener()
        setupObserver()
        playAnimation()
    }

    private fun setupListener() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            when {
                email.isEmpty() -> {
                    setEmailError(getString(R.string.must_filled))
                }
                password.length < 6 -> {
                    setPasswordError(getString(R.string.error_password_not_valid))
                }
                else -> {
                    val login = LoginRequest(email, password)
                    loginViewModel.login(login)
                }
            }
        }
        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun setupObserver() {
        loginViewModel.loginResponse.observe(this) { loginResponse ->
            when (loginResponse) {
                is Result.Loading -> {
                    onLoading(true)
                }
                is Result.Success -> loginResponse.data?.loginResult?.let {
                    onLoading(false)
                    onSuccess(it)
                }
                is Result.Error -> loginResponse.data.let {
                    onLoading(false)
                    onFailed()
                }
            }
        }
    }

    private fun onLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun onSuccess(loginResult: LoginResult) {
        loginViewModel.saveUser(loginResult.token)
        Toast.makeText(this, getString(R.string.login_success, loginResult.name), Toast.LENGTH_LONG).show()

        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_TOKEN, loginResult.token)
        startActivity(intent)
        finish()
    }

    private fun onFailed() {
        Snackbar.make(binding.root, getString(R.string.login_failed), Snackbar.LENGTH_LONG).show()
    }

    private fun setEmailError(e : String?){
        binding.emailInput.error = e
    }

    private fun setPasswordError(e: String?){
        binding.passwordInput.error = e
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageLogo, View.TRANSLATION_X, -60f, 60f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }

}