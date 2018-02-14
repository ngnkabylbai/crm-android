package kz.mycrm.android

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kz.mycrm.android.ui.BaseActivity
import kz.mycrm.android.ui.intro.IntroActivity
import kz.mycrm.android.ui.login.loginIntent
import kz.mycrm.android.ui.main.division.divisionsIntent
import kz.mycrm.android.util.Constants

class SplashActivity : BaseActivity() {

    private var isAuthenticated = false
    private lateinit var viewModel: SplashViewModel
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fabric.with(this, Crashlytics())

        sharedPref = getSharedPreferences(getString(R.string.app_mycrm_shared_key), Context.MODE_PRIVATE)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.getAuthentication().observe(this, Observer { isAuthenticated ->
            run {
                this.isAuthenticated = isAuthenticated!!
                invalidate()
            }
        })

        viewModel.checkAuthentication()
    }

    private fun invalidate() {
        if(BuildConfig.DEBUG || !wasIntroShown()) {
            startActivityForResult(Intent(this, IntroActivity::class.java), Constants.introRequestCode)
        } else {
            loadNextActivity()
        }
    }

    private fun loadNextActivity() {
        if (isAuthenticated) {
            val isInterNetAvailable = isInternetAvailable()
            if (isInterNetAvailable) {
                startActivity(divisionsIntent())
                finish()
            } else {
                showMessage("Нет подключения к сети")
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
        when(requestCode) {
            Constants.introRequestCode -> {
                saveState()
                loadNextActivity()
            }
        }
    }
}
