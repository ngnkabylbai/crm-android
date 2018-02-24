package kz.mycrm.android

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kz.mycrm.android.ui.BaseActivity
import kz.mycrm.android.ui.intro.IntroActivity
import kz.mycrm.android.ui.login.loginIntent
import kz.mycrm.android.ui.main.division.DivisionsActivity
import kz.mycrm.android.ui.main.division.divisionsIntent
import kz.mycrm.android.util.Constants
import kz.mycrm.android.util.Logger

class SplashActivity : BaseActivity() {

    private var isAuthenticated = false
    private lateinit var viewModel: SplashViewModel
    private lateinit var sharedPref: SharedPreferences
    private var mIntent: Intent? = null

    private var pushAction = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Fabric.with(this, Crashlytics())

        mIntent = intent

        sharedPref = getSharedPreferences(getString(R.string.app_mycrm_shared_key), Context.MODE_PRIVATE)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.getAuthentication().observe(this, Observer { isAuthenticated ->
            run {
                this.isAuthenticated = isAuthenticated!!
                invalidate()
            }
        })

        dialogManager = MaterialDialog.Builder(this)
                .positiveText(R.string.dialog_ok)

        viewModel.checkAuthentication()
    }

    private fun invalidate() {
        pushAction = mIntent?.extras != null
        if(pushAction || viewModel.wasIntroShown(this, sharedPref) || !BuildConfig.MOCK) {
            startNextActivity()
        } else {
            startActivityForResult(Intent(this, IntroActivity::class.java), Constants.introRequestCode)
        }
    }

    private fun startNextActivity() {
        if (isAuthenticated) {
            if(pushAction) {
                startPushActivity()
            } else {
                startDivisionsActivity()
            }
        } else {
            Logger.debug("Starting loginActivity")
            startActivityForResult(loginIntent(), Constants.loginRequestCode)
        }
    }

    private fun startPushActivity() {
        val startDivisionIntent = Intent(this, DivisionsActivity::class.java)
        val bundle = mIntent!!.extras
        startDivisionIntent.putExtras(bundle)
        startActivity(startDivisionIntent)
        finish()
        return
    }

    private fun startDivisionsActivity() {
        startActivity(divisionsIntent())
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Logger.debug("onActivityResult: requestCode $requestCode, resultCode $resultCode")
        if(resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Constants.introRequestCode -> {
                    viewModel.saveState(this, sharedPref)
                    startNextActivity()
                }
                Constants.loginRequestCode -> {
                    if (pushAction) {
                        startPushActivity()
                    } else {
                        startDivisionsActivity()
                    }
                }
            }
        } else {
            finish()
        }
    }
}
