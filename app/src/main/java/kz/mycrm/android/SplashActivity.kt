package kz.mycrm.android

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import kz.mycrm.android.ui.BaseActivity
import kz.mycrm.android.ui.login.loginIntent
import kz.mycrm.android.ui.main.division.divisionsIntent
import kz.mycrm.android.util.Logger

class SplashActivity : BaseActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.checkForToken().observe(this, Observer { token ->
            if(token != null) {
                Logger.debug("Token found from db: Rec count:" + MycrmApp.database.TokenDao().getCount()+" data: " + token.token)
                startActivity(divisionsIntent())
            } else {
                Logger.debug("Token wasn't found. Directing to login activity...")
                startActivity(loginIntent())
            }
            finish()
        })
    }
}
