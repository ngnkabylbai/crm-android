package kz.mycrm.android.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_login2.*
import kz.mycrm.android.BuildConfig
import kz.mycrm.android.R
import kz.mycrm.android.remote.OnConnectionTimeoutListener
import kz.mycrm.android.remote.RetrofitClient
import kz.mycrm.android.ui.BaseActivity
import kz.mycrm.android.ui.main.division.divisionsIntent
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Status
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.ViewSwitcher


/**
 * Created by NKabylbay on 11/11/2017.
 */

fun Context.loginIntent(): Intent {
    return Intent(this, LoginActivity::class.java)
}

enum class LoginState {
    Login, PhoneEnter, ApproveCode, NewPass, Loading
}

class LoginActivity : BaseActivity(), View.OnClickListener, OnConnectionTimeoutListener {


    private lateinit var viewModel: LoginViewModel
    private var screenHeight = 0
    private var screenWidth = 0
    private var activityState = LoginState.Login
    private var smallScreen = false
    private var keyboardIsOpen = false

    private val mHandler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(message: Message) {
            builder.setNegativeButton(R.string.ok, { dialog, id ->
                login.error = null
            })
            showMessage(resources.getString(R.string.error_timeout))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
        setContentView(R.layout.activity_login2)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.requestToken().observe(this, Observer { token ->
            when(token!!.status) {
                Status.LOADING -> onLoading()
                Status.SUCCESS -> onSuccess()
                Status.ERROR -> onError()
            }
        })

        loginButton.setOnClickListener(this)
        password.setOnClickListener(this)
        forgotPassword.setOnClickListener(this)

        if(BuildConfig.DEBUG) {
//            login.setText("+7 701 381-71-15")
//            password.setText("password")
            login.setText("+7 707 830-69-24")
            password.setText("yeruuh")
        }

        builder = AlertDialog.Builder(this)
        builder.create()
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

        forgotPassword.setOnClickListener {
            setEnterPhoneState()
        }

        loginButtonTextSwitcher.inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        loginButtonTextSwitcher.setFactory(mFactory)
        setDefaultState()
    }

    private fun obtainScreenSize() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels
        screenWidth = displayMetrics.widthPixels
        Logger.debug("Screen WIDTH: $screenWidth")
        Logger.debug("Screen HEIGHT: $screenHeight")
    }

    private fun setDefaultState() {
        activityState = LoginState.Login
        invalidateLogo()
        newPasswordLayout.visibility = View.GONE
        loginLayout.visibility = View.VISIBLE
        requestFocusToLogin()
        forgotPassword.visibility = View.VISIBLE
        hintText.startAnimation(getTextAnim(resources.getString(R.string.hint_authorize)))
        passwordLayoutParent.visibility = View.VISIBLE
        password.setText("")
        resenText.visibility = View.GONE
        loginButtonTextSwitcher.setCurrentText(getButtonText())
    }

    private fun setEnterPhoneState() {
        activityState = LoginState.PhoneEnter
        invalidateLogo()
        hintText.startAnimation(getTextAnim(resources.getString(R.string.hint_forgot)))
        passwordLayoutParent.visibility = View.GONE
        loginButtonTextSwitcher.setText(getButtonText())
    }

    private fun setApproveState() {
        activityState = LoginState.ApproveCode
        invalidateLogo()
        forgotPassword.visibility = View.GONE
        loginButtonTextSwitcher.setText(getButtonText())
        passwordLayoutParent.visibility = View.VISIBLE
        resenText.visibility = View.VISIBLE
    }

    private fun setNewPassState() {
        activityState = LoginState.NewPass
        invalidateLogo()
        loginButtonTextSwitcher.setText(getButtonText())
        hintText.startAnimation(getTextAnim(resources.getString(R.string.hint_approved)))
        forgotPassword.visibility = View.GONE
        loginLayout.visibility = View.GONE
        newPasswordLayout.visibility = View.VISIBLE
        newPassword.requestFocus()
        passwordLayoutParent.visibility = View.VISIBLE
    }

    private fun onLoading() {
        progress.visibility = View.VISIBLE
        loginButtonTextSwitcher.setText(getButtonText())
        activityState = LoginState.Loading
    }

    private fun getButtonText(): String {
        return when (activityState) {
            LoginState.Loading -> ""
            LoginState.Login -> resources.getString(R.string.action_login)
            LoginState.PhoneEnter -> resources.getString(R.string.action_send)
            LoginState.ApproveCode -> resources.getString(R.string.action_approve)
            LoginState.NewPass -> resources.getString(R.string.action_save_and_login)
        }
    }

    private fun onSuccess() {
        startActivity(divisionsIntent())
        finish()
        activityState = LoginState.Loading
    }

    private fun onError() {
        login.error = resources.getString(R.string.error_invalid_password)
        requestFocusToLogin()
        progress.visibility = View.GONE
        loginButtonTextSwitcher.setText(getButtonText())
    }

    override fun onClick(v: View?) {
        login.error = null
        when (activityState) {
            LoginState.Loading -> return
            LoginState.Login -> {
                    if (isValidInput()) {
                        activityState = LoginState.Loading
                        loginButtonTextSwitcher.setText(getButtonText())
                        viewModel.updateAuthData(login.text.toString(), password.text.toString())
                        viewModel.startRefresh()
                    }
                }
            LoginState.PhoneEnter -> { setApproveState() } // send request
            LoginState.ApproveCode -> { setNewPassState() } // get new password and renew
            LoginState.NewPass -> {
                password.requestFocus()
                setDefaultState()
            } // login
        }
    }

    private fun isValidInput(): Boolean {
        if (login.text.length != 16 || password.text.isEmpty()) {
            login.error = resources.getString(R.string.error_empty_string)
            requestFocusToLogin()
            return false
        }
        return true
    }

    private fun requestFocusToLogin() {
        login.requestFocus()
        login.setSelection(login.text.length)
    }

    private fun getTextAnim(text: String): AlphaAnimation {
        val anim = AlphaAnimation(1.0f, 0.0f)
        anim.duration = 200
        anim.repeatCount = 1
        anim.repeatMode = Animation.REVERSE

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) { }
            override fun onAnimationStart(animation: Animation?) { }
            override fun onAnimationRepeat(animation: Animation?) {
                hintText.text = text
            }
        })

        return anim
    }

    private fun invalidateLogo() {
        if(keyboardIsOpen) {
            if(smallScreen)
                logo.visibility = View.GONE
        } else {
            logo.visibility = View.VISIBLE
        }
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    override fun onConnectionTimeout() {
        val msg = mHandler.obtainMessage()
        msg.sendToTarget()
    }

    private val mFactory = object : ViewSwitcher.ViewFactory {

        override fun makeView(): View {
            val view = TextView(this@LoginActivity)
            view.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            view.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
            return view
        }
    }
}

