package kz.mycrm.android.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AlertDialog
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_login.*
import kz.mycrm.android.BuildConfig
import kz.mycrm.android.R
import kz.mycrm.android.remote.OnConnectionTimeoutListener
import kz.mycrm.android.remote.RetrofitClient
import kz.mycrm.android.ui.BaseActivity
import kz.mycrm.android.ui.main.division.divisionsIntent
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Status
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent

/**
 * Created by NKabylbay on 11/11/2017.
 */

fun Context.loginIntent(): Intent {
    return Intent(this, LoginActivity::class.java)
}

enum class LoginState {
    Default, PhoneEnter, ApproveCode, NewPass, Loading, NewPassDone
}

class LoginActivity : BaseActivity(), View.OnClickListener, OnConnectionTimeoutListener {


    private lateinit var viewModel: LoginViewModel
    private var screenHeight = 0
    private var screenWidth = 0
    private var activityState = LoginState.Default
    private var smallScreen = false;

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
        setContentView(R.layout.activity_login)

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

//        val layoutTransition = loginParentLayout.layoutTransition ?: LayoutTransition()
//        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
//        layoutTransition.setDuration(400)
//        loginParentLayout.layoutTransition = layoutTransition

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels
        screenWidth = displayMetrics.widthPixels
        Logger.debug("Screen WIDTH: $screenWidth")
        Logger.debug("Screen HEIGHT: $screenHeight")

        if(screenHeight <= 800)
            smallScreen = true

        KeyboardVisibilityEvent.setEventListener(this) { isOpen ->
            if(isOpen) {
                Logger.debug("SoftKeyboard is UP")
                invalidateHintText()
            } else {
                Logger.debug("SoftKeyboard is DOWN")
                    hintText.visibility = View.VISIBLE
            }
        }

        forgotPassword.setOnClickListener {
            hideKeyboard()
            setEnterPhoneState()
        }
        logo.setOnClickListener { setDefaultState() }
    }

    private fun setDefaultState() {
        invalidateHintText()
        newPasswordLayout.visibility = View.INVISIBLE
        loginLayout.visibility = View.VISIBLE
        requestFocusToLogin()
        forgotPassword.visibility = View.VISIBLE
        hintText.startAnimation(getTextAnim(resources.getString(R.string.hint_authorize)))
        loginButton.text = resources.getText(R.string.action_login)
        passwordLayoutParent.visibility = View.VISIBLE
        password.setText("")
        resenText.visibility = View.INVISIBLE
        activityState = LoginState.Default
    }

    private fun setEnterPhoneState() {
        invalidateHintText()
        hintText.startAnimation(getTextAnim(resources.getString(R.string.hint_forgot)))
        loginButton.text = resources.getText(R.string.action_send)
        passwordLayoutParent.visibility = View.INVISIBLE
        activityState = LoginState.ApproveCode
    }

    private fun setApproveState() {
        invalidateHintText()
        loginButton.text = resources.getText(R.string.action_approve)
        forgotPassword.visibility = View.INVISIBLE
        passwordLayoutParent.visibility = View.VISIBLE
        activityState = LoginState.NewPass
        resenText.visibility = View.VISIBLE
    }

    private fun setNewPassState() {
        invalidateHintText()
        loginButton.text = resources.getText(R.string.action_approve)
        forgotPassword.visibility = View.INVISIBLE
        loginLayout.visibility = View.INVISIBLE
        newPasswordLayout.visibility = View.VISIBLE
        newPassword.requestFocus()
        passwordLayoutParent.visibility = View.VISIBLE
        activityState = LoginState.NewPassDone
    }

    private fun onLoading() {
        progress.visibility = View.VISIBLE
        loginButton.startAnimation(fadeAnimation(false))
        activityState = LoginState.Loading
    }

    private fun onSuccess() {
        startActivity(divisionsIntent())
        finish()
        activityState = LoginState.Loading
    }

    private fun onError() {
        login.error = resources.getString(R.string.error_invalid_password)
        requestFocusToLogin()
        progress.visibility = View.INVISIBLE
        loginButton.startAnimation(fadeAnimation(true))
    }

    override fun onClick(v: View?) {
        login.error = null
        when (activityState) {
            LoginState.Loading -> return
            LoginState.Default -> {
                    if (isValidInput()) {
                        viewModel.updateAuthData(login.text.toString(), password.text.toString())
                        viewModel.startRefresh()
                    }
                }
            LoginState.PhoneEnter -> { setEnterPhoneState() } // send request
            LoginState.ApproveCode -> { setApproveState() } // get new password and renew
            LoginState.NewPass -> { setNewPassState() } // login
            LoginState.NewPassDone -> {
                setDefaultState()
                password.requestFocus()
            }
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

    private fun fadeAnimation(fadeIn: Boolean): Animation {
        val animation = if (fadeIn)
                AlphaAnimation(0f, 1.0f)
            else
                AlphaAnimation(1.0f, 0f)

        animation.duration = 100
        animation.isFillEnabled = true
        animation.fillAfter = true
        return animation
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

    private fun invalidateHintText() {
        if(smallScreen)
            hintText.visibility = View.GONE
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
}

