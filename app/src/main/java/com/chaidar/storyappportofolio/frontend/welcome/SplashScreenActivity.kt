package com.chaidar.storyappportofolio.frontend.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.chaidar.storyappportofolio.backend.data.preferences.UserPreference
import com.chaidar.storyappportofolio.backend.data.preferences.dataStore
import com.chaidar.storyappportofolio.databinding.ActivitySplashScreenBinding
import com.chaidar.storyappportofolio.frontend.ViewModelFactory
import com.chaidar.storyappportofolio.frontend.main.MainActivity
import com.chaidar.storyappsubmis.frontend.login.LoginActivity


class SplashScreenActivity : AppCompatActivity() {

    private val welcomeViewModel by viewModels<WelcomeViewModel> {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        welcomeViewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                UserPreference.setToken(user.tokenAuth)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}