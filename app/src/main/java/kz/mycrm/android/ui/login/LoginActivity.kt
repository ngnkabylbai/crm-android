package kz.mycrm.android.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kz.mycrm.android.R

/**
 * Created by NKabylbay on 11/11/2017.
 */

fun Context.loginIntent(): Intent {
    return Intent(this, LoginActivity::class.java)
}

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private var login = "+7 701 381 71 15"
    private var password = "password"

    // TODO: design
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.requestToken(login, password)
                .observe(this, Observer { token ->
//                    TODO: handle it
                })
    }
}