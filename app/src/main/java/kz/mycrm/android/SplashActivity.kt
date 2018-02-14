package kz.mycrm.android

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kz.mycrm.android.db.entity.AppVersion
import kz.mycrm.android.ui.BaseActivity
import kz.mycrm.android.ui.intro.IntroActivity
import kz.mycrm.android.ui.login.loginIntent
import kz.mycrm.android.ui.main.division.divisionsIntent
import kz.mycrm.android.util.Constants
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource
import kz.mycrm.android.util.Status

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
        viewModel.getAppVersion().observe(this, Observer { appVersion ->
            when(appVersion?.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> onResponse(appVersion)
                Status.ERROR -> viewModel.checkAuthentication()
            }
        })
        viewModel.getAuthentication().observe(this, Observer { isAuthenticated ->
            run {
                startMain = isAuthenticated!!
                invalidate()
            }
        })

        viewModel.checkAppVersion()
    }

    private fun invalidate() {
        if(BuildConfig.DEBUG || !wasIntroShown()) {
            startActivityForResult(Intent(this, IntroActivity::class.java), Constants.introRequestCode)
        } else {
            loadNextActivity()
        }
    }

    private fun onResponse(appVersion: Resource<AppVersion>) {
        Logger.debug("AppVersion got:" + appVersion.data?.version)
        if(appVersion.data != null && BuildConfig.VERSION_NAME == appVersion.data.version) {
            viewModel.checkAuthentication()
        } else {
            try {
                redirectToMarket()
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            } finally {

            }
        }
    }

    private fun loadNextActivity() {
        if (startMain) {
            val isInterNetAvailable = isInternetAvailable()
            if (isInterNetAvailable) {
                startActivity(divisionsIntent())
                finish()
            } else {
                showMessage("Нет подключения к сети")
//                Toast.makeText(this, "Нет подключения к сети", Toast.LENGTH_SHORT).show()
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
            Constants.marketRequestCode -> {
                viewModel.checkAppVersion()
            }
        }
    }
}
