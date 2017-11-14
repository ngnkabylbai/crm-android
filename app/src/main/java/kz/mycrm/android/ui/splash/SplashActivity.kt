package kz.mycrm.android.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kz.mycrm.android.R
import kz.mycrm.android.ui.login.loginIntent

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.checkForToken().observe(this, Observer { hasToken ->
            if(hasToken!!) {
//                TODO: Direct to mainActivity
            } else {
                startActivity(loginIntent())
                finish()
            }
        })
    }
}
