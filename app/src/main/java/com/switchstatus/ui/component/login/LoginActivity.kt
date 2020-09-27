package com.switchstatus.ui.component.login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.switchstatus.data.Resource
import com.switchstatus.data.dto.login.LoginResponse
import com.switchstatus.databinding.LoginActivityBinding
import com.switchstatus.ui.ViewModelFactory
import com.switchstatus.ui.base.BaseActivity
import com.switchstatus.ui.component.recipes.RecipesListActivity
import com.switchstatus.utils.*
import javax.inject.Inject

/**
 * Created by AhmedEltaher
 */

class LoginActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: LoginActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.login.setOnClickListener { doLogin() }
    }

    override fun initViewBinding() {
        binding = LoginActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun initializeViewModel() {
        loginViewModel = viewModelFactory.create(loginViewModel::class.java)
    }

    override fun observeViewModel() {
        observe(loginViewModel.loginLiveData, ::handleLoginResult)
        observeSnackBarMessages(loginViewModel.showSnackBar)
        observeToast(loginViewModel.showToast)
    }

    private fun doLogin() {
        loginViewModel.doLogin(binding.username.text.trim().toString(),
                binding.password.text.toString())
    }

    private fun handleLoginResult(status: Resource<LoginResponse>) {
        when (status) {
            is Resource.Loading -> binding.loaderView.toVisible()
            is Resource.Success -> status.data?.let {
                binding.loaderView.toGone()
                navigateToMainScreen()
            }
            is Resource.DataError -> {
                binding.loaderView.toGone()
                status.errorCode?.let { loginViewModel.showToastMessage(it) }
            }
        }
    }

    private fun navigateToMainScreen() {
        val nextScreenIntent = Intent(this, RecipesListActivity::class.java)
        startActivity(nextScreenIntent)
        finish()
    }

    private fun observeSnackBarMessages(event: LiveData<SingleEvent<Any>>) {
        binding.root.setupSnackbar(this, event, Snackbar.LENGTH_LONG)
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }
}
