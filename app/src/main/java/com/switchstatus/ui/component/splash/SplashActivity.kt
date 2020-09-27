package com.switchstatus.ui.component.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.switchstatus.databinding.SplashLayoutBinding
import com.switchstatus.ui.ViewModelFactory
import com.switchstatus.ui.base.BaseActivity
import com.switchstatus.ui.component.login.LoginActivity
import com.switchstatus.SPLASH_DELAY
import com.switchstatus.ui.component.switches.SwitchesListActivity
import javax.inject.Inject

/**
 * Created by AhmedEltaher
 */

class SplashActivity : BaseActivity(){
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var splashViewModel: SplashViewModel

    private lateinit var binding: SplashLayoutBinding

    override fun initViewBinding() {
        binding = SplashLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun initializeViewModel() {
        splashViewModel = viewModelFactory.create(splashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMainScreen()
    }

    override fun observeViewModel() {

    }

    private fun navigateToMainScreen() {
        Handler().postDelayed({
            val nextScreenIntent = Intent(this, SwitchesListActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }, SPLASH_DELAY.toLong())
    }
}
