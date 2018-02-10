package kz.mycrm.android

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import kz.mycrm.android.ui.BaseActivity
import kz.mycrm.android.ui.intro.IntroActivity
import kz.mycrm.android.ui.login.loginIntent
import kz.mycrm.android.ui.main.division.divisionsIntent
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

class SplashActivity : BaseActivity() {

    private lateinit var viewModel: SplashViewModel
    private var startMain = false
    private lateinit var sharedPref: SharedPreferences

    //    TODO: Refactor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fabric.with(this, Crashlytics())

        sharedPref = getSharedPreferences(getString(R.string.app_mycrm_shared_key), Context.MODE_PRIVATE)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.checkAuthentication().observe(this, Observer { isAuthenticated ->
            run {
                startMain = isAuthenticated!!
            }
        })

        if(BuildConfig.DEBUG || wasIntroShown()) {
            startActivityForResult(Intent(this, IntroActivity::class.java), 1)
        } else {
            loadNextActivity()
        }
    }

    private fun loadNextActivity() {
        if (startMain) {
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
    }

    private fun wasIntroShown(): Boolean {
        val defaultValue = getString(R.string.app_mycrm_shared_intro_default)
        val sharedValue = sharedPref.getString(getString(R.string.app_mycrm_shared_intro), defaultValue)

        return sharedValue == defaultValue
    }

    private fun saveState() {
        val editor = sharedPref.edit()
        editor.putString(getString(R.string.app_mycrm_shared_intro), getString(R.string.app_mycrm_shared_intro_shown))
        editor.apply()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        saveState()
        loadNextActivity()
//        if (isInternetAvailable()) {
//
//
////            viewModel.checkAuthentication().observe(this, Observer { token ->
////                if (token != null) {
////                    startActivity(divisionsIntent()) // for MVP
////                    setSuccess()
////                } else {
////                    Logger.debug("Token wasn't found. Directing to login activity...")
////                    startActivity(loginIntent())
////                    setSuccess()
////                }
////            })
//        } else {
//            Toast.makeText(this, "Нет подключения к сети", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}
