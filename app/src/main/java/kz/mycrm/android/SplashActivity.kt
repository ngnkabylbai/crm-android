package kz.mycrm.android

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import kz.mycrm.android.ui.BaseActivity
import kz.mycrm.android.ui.intro.IntroActivity
import kz.mycrm.android.ui.login.loginIntent
import kz.mycrm.android.ui.main.division.divisionsIntent
import kz.mycrm.android.util.ApiUtils

fun Context.splashIntent(): Intent {
    return Intent(this, SplashActivity::class.java)
}

class SplashActivity : BaseActivity() {

    private lateinit var viewModel: SplashViewModel

    //    TODO: Refactor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)

        startActivityForResult(Intent(this, IntroActivity::class.java), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.checkAuthentication().observe(this, Observer { isAuthenticated ->
            if (isAuthenticated!!) {
                if (isInternetAvailable()) {
                    startActivity(divisionsIntent())
                    finish()
                } else {
                    Toast.makeText(this, "Нет подключения к сети", Toast.LENGTH_SHORT).show()
                }
            } else {
                startActivity(loginIntent())
                finish()
            }
        })

        if (isInternetAvailable()) {


//            viewModel.checkAuthentication().observe(this, Observer { token ->
//                if (token != null) {
//                    startActivity(divisionsIntent()) // for MVP
//                    finish()
//                } else {
//                    Logger.debug("Token wasn't found. Directing to login activity...")
//                    startActivity(loginIntent())
//                    finish()
//                }
//            })
        } else {
            Toast.makeText(this, "Нет подключения к сети", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}
