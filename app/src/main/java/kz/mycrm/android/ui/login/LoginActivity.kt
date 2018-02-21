package kz.mycrm.android.ui.login

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.ViewSwitcher
import com.afollestad.materialdialogs.MaterialDialog
import com.crashlytics.android.Crashlytics
import kotlinx.android.synthetic.main.activity_login2.*
import kz.mycrm.android.BuildConfig
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.remote.OnConnectionTimeoutListener
import kz.mycrm.android.remote.RetrofitClient
import kz.mycrm.android.ui.BaseActivity
import kz.mycrm.android.ui.forgot.password.forgotPasswordIntent
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource
import kz.mycrm.android.util.Status
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by NKabylbay on 11/11/2017.
 */

fun Context.loginIntent(): Intent {
    return Intent(this, LoginActivity::class.java)
}

class LoginActivity : BaseActivity(), OnConnectionTimeoutListener {

    private lateinit var viewModel: LoginViewModel
    private var screenHeight = 0
    private var screenWidth = 0
    private var smallScreen = false
    private var keyboardIsOpen = false

    private val forgotPasswordRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        setContentView(R.layout.activity_login2)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.requestToken().observe(this, Observer { token ->
            when(token!!.status) {
                Status.LOADING -> onLoading()
                Status.SUCCESS -> onSuccess()
                Status.ERROR -> onError(token)
            }
        })

        dialogManager = MaterialDialog.Builder(this)
                .positiveText(R.string.dialog_ok)

        RetrofitClient.setConnectionTimeoutListener(this)

        obtainScreenSize()

        if(screenHeight <= 800)
            smallScreen = true

        KeyboardVisibilityEvent.setEventListener(this) { isOpen ->
            keyboardIsOpen = isOpen
            if(isOpen)
                Logger.debug("SoftKeyboard is DOWN")
            else
                Logger.debug("SoftKeyboard is UP")

            invalidateLogo()
        }

        loginButton.setOnClickListener {
            if(isInternetAvailable()) {
                loginEditText.error = null
                if (isValidInput()) {
                    viewModel.updateAuthData(loginEditText.text.toString(), reEnterPasswordEditText.text.toString())
                    viewModel.startRefresh()
                } else {
                    loginEditText.error = resources.getString(R.string.error_empty_string)
                    requestFocusToLogin()
                }
            } else {
                showNoInternetMessage()
            }
        }

        forgotPassword.setOnClickListener {
            if(isInternetAvailable()) {
                val intent = forgotPasswordIntent()
                intent.putExtra("screen_height", screenHeight)
                startActivityForResult(intent, forgotPasswordRequestCode)
            } else {
                showNoInternetMessage()
            }
        }

        loginButtonTextSwitcher.inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        loginButtonTextSwitcher.setFactory(mFactory)

        loginButtonTextSwitcher.setCurrentText(getString(R.string.action_login))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK) {
            onSuccess()
        }
    }

    override fun onResume() {
        super.onResume()
        if(BuildConfig.DEBUG) {
//            login.setText("+7 701 381-71-15")
//            password.setText("password")
//            loginEditText.setText("+7 707 830-69-24")
//            reEnterPasswordEditText.setText("yeruuh")
            loginEditText.setText("+7 771 015-15-11")
            reEnterPasswordEditText.setText("password")
        }
    }

    private fun obtainScreenSize() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels
        screenWidth = displayMetrics.widthPixels
        Logger.debug("Screen WIDTH: $screenWidth")
        Logger.debug("Screen HEIGHT: $screenHeight")
    }

    private fun onLoading() {
        loginProgressBar.visibility = View.VISIBLE
        loginButtonTextSwitcher.setText(getText(R.string.empty_string))
    }

    private fun onSuccess() {
        val sharedPref = getSharedPreferences(getString(R.string.app_mycrm_shared_key), Context.MODE_PRIVATE)
        val defaultValue = getString(R.string.empty_string)
        val key = sharedPref.getString(getString(R.string.app_mycrm_notification_token), defaultValue)

        if(key != defaultValue) {
            viewModel.sendNotificationToken(key)
        } else {
            Crashlytics.log(Log.ERROR, getString(R.string.app_name), "LoginActivity: Notification key is null while sending to server")
        }

        finishWithOk()
    }

    private fun finishWithOk() {
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun onError(response: Resource<Token>) {
        try {
            val responseJSON = JSONArray(response.message)[0] as JSONObject
            loginEditText.error = responseJSON.getString("message")
        } catch (e: Exception) {
            Crashlytics.logException(e)
            loginEditText.error = getString(R.string.error)
        }

        requestFocusToLogin()
        loginProgressBar.visibility = View.GONE
        loginButtonTextSwitcher.setText(getText(R.string.action_login))
    }

    private fun showNoInternetMessage() {
        hideKeyboard()
        showMessage("Нет подключения к сети")
    }

    private fun isValidInput(): Boolean {
        return loginEditText.text.length == 16 && !reEnterPasswordEditText.text.isEmpty()
    }

    private fun requestFocusToLogin() {
        loginEditText.requestFocus()
        loginEditText.setSelection(loginEditText.text.length)
    }

    private fun invalidateLogo() {
        if(keyboardIsOpen) {
            if(smallScreen)
                logoImageView.visibility = View.GONE
        } else {
            logoImageView.visibility = View.VISIBLE
        }
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    override fun onConnectionTimeout() { }

    private val mFactory = object : ViewSwitcher.ViewFactory {

        override fun makeView(): View {
            val view = TextView(this@LoginActivity)
            view.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            view.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
            return view
        }
    }
}

