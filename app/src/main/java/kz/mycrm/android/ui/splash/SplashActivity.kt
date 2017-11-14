package kz.mycrm.android.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kz.mycrm.android.R
import kz.mycrm.android.ui.login.LoginViewModel

class SplashActivity : AppCompatActivity() {

    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.checkForToken().observe(this, Observer { hasToken ->
            if(hasToken!!) {
                Toast.makeText(this, "There is a token", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No token", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
