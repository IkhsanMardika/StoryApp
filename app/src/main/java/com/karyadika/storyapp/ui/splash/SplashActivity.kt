package com.karyadika.storyapp.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.karyadika.storyapp.ui.main.MainActivity
import com.karyadika.storyapp.ui.user.login.LoginActivity
import com.karyadika.storyapp.utils.UserViewModelFactory

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels{
        UserViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        validateUser()
    }

    private fun validateUser() {
        viewModel.fetchUser().observe(this){ token ->
            if (token != ""){
                startActivity(Intent(this@SplashActivity, MainActivity::class.java).putExtra(
                    MainActivity.EXTRA_TOKEN, token))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        }
    }



}