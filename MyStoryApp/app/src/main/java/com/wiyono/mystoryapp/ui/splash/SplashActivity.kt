package com.wiyono.mystoryapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.wiyono.mystoryapp.R
import com.wiyono.mystoryapp.ui.auth.AuthActivity
import com.wiyono.mystoryapp.ui.main.MainActivity
import com.wiyono.mystoryapp.viewmodel.ViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel = ViewModelProvider(this, ViewModelFactory(this))[SplashViewModel::class.java]

        lifecycleScope.launch {
            delay(3000)
            splashViewModel.isLogin().observe(this@SplashActivity) { isLogin ->
                if (isLogin) {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SplashActivity, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}
