package kz.mycrm.android.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kz.mycrm.android.R
import kz.mycrm.android.application.MycrmApp
import kz.mycrm.android.ui.login.loginIntent
import kz.mycrm.android.util.Logger

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.checkForToken().observe(this, Observer { token ->
            if(token != null) {
//                TODO: Direct to mainActivity
                Logger.debug("Token found from db: Rec count:" + MycrmApp.database.TokenDao().getCount()+" data: " + token.token)
            } else {
                Logger.debug("Token wasn't found. Directing to login activity...")
                startActivity(loginIntent())
                finish()
            }
        })
    }
}
