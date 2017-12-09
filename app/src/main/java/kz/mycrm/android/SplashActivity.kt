package kz.mycrm.android

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kz.mycrm.android.ui.BaseActivity
import kz.mycrm.android.ui.login.loginIntent
import kz.mycrm.android.ui.main.division.divisionsIntent
import kz.mycrm.android.ui.main.mainIntent
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Status
import java.io.IOException
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL

fun Context.splashIntent(): Intent {
    return Intent(this, SplashActivity::class.java)
}

class SplashActivity : BaseActivity() {

    private lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        if (isInternetAvailable()) {
            viewModel.checkForToken().observe(this, Observer { token ->
                if (token != null) {
                    viewModel.loadUserDivisions(token.token).observe(this,
                            Observer { resourceDivisionList ->
                                if (resourceDivisionList?.status == Status.SUCCESS) {
                                    if (resourceDivisionList.data != null) {
                                        Logger.debug("resource" + resourceDivisionList.data.size)
                                        if (resourceDivisionList.data.size > 1) {
                                            startActivity(divisionsIntent())
                                            finish()
                                        } else {
                                            if (resourceDivisionList.data.size == 1) {
                                                val intent = mainIntent()
                                                intent.putExtra("division_id", resourceDivisionList.data[0].id)
                                                startActivity(intent)
                                                finish()
                                            }
                                        }
                                    } else {
                                        Toast.makeText(this, "No divisions available", Toast.LENGTH_SHORT).show()
                                        startActivity(loginIntent())
                                    }
                                }
                            })
                } else {
                    Logger.debug("Token wasn't found. Directing to login activity...")
                    startActivity(loginIntent())
                    finish()
                }
            })
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
