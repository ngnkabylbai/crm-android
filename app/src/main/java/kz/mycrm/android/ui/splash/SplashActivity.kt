package kz.mycrm.android.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kz.mycrm.android.R

class SplashActivity : AppCompatActivity() {

    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.checkForToken().observe(this, Observer { hasToken ->
            if(hasToken!!) {

            } else {

            }
        })


    }
}
