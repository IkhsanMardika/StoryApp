package com.karyadika.storyapp.ui.user.register

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.karyadika.storyapp.R
import com.karyadika.storyapp.data.remote.user.register.RegisterRequest
import com.karyadika.storyapp.databinding.ActivityRegisterBinding
import com.karyadika.storyapp.utils.Result
import com.karyadika.storyapp.utils.UserViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val regViewModel: RegisterViewModel by viewModels(){
        UserViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupListener()
        setupObserver()
        playAnimation()
    }

    private fun setupListener() {
        binding.registerButton.setOnClickListener {
            val name = binding.nameInput.text.toString().trim()
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
                    val register = RegisterRequest(name, email, password)
                    regViewModel.register(register)
                }
            }
        }
    }

    private fun setupObserver() {
        regViewModel.registerResponse.observe(this){ registerResponse ->
            when(registerResponse) {
                is Result.Loading -> {
                    onLoading(true)
                }
                is Result.Success -> registerResponse.data.let {
                    onLoading(false)
                    onSuccess()
                }

                is Result.Error -> registerResponse.data.let {
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

    private fun onSuccess() {
        Snackbar.make(binding.root, getString(R.string.register_success), Snackbar.LENGTH_LONG).show()
    }

    private fun onFailed() {
        Snackbar.make(binding.root, getString(R.string.register_failed), Snackbar.LENGTH_LONG).show()
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