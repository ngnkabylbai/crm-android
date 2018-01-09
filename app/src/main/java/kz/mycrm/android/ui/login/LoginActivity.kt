package kz.mycrm.android.ui.login

import android.animation.LayoutTransition
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_login.*
import kz.mycrm.android.BuildConfig
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.R
import kz.mycrm.android.remote.OnConnectionTimeoutListener
import kz.mycrm.android.remote.RetrofitClient
import kz.mycrm.android.ui.BaseActivity
import kz.mycrm.android.ui.main.division.divisionsIntent
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Status


/**
 * Created by NKabylbay on 11/11/2017.
 */

fun Context.loginIntent(): Intent {
    return Intent(this, LoginActivity::class.java)
}

class LoginActivity : BaseActivity(), View.OnClickListener, OnConnectionTimeoutListener {

    private lateinit var viewModel: LoginViewModel
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

        ButterKnife.bind(this)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.requestToken().observe(this, Observer { token ->
            when(token!!.status) {
                Status.LOADING -> onLoading()
                Status.SUCCESS -> onSuccess()
                Status.ERROR -> onError()
            }
        })

        if(BuildConfig.DEBUG) {
//            login.setText("+7 701 381-71-15")
//            password.setText("password")
            login.setText("+7 707 830-69-24")
            password.setText("yeruuh")
        }

        builder = AlertDialog.Builder(this)
        builder.create()
        RetrofitClient.setConnectionTimeoutListener(this)

        val layoutTransition = loginParentLayout.layoutTransition ?: LayoutTransition()
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        layoutTransition.setDuration(100)
        loginParentLayout.layoutTransition = layoutTransition
    }

    private fun onLoading() {
        progress.visibility = View.VISIBLE
        loginButton.startAnimation(fadeAnimation(false))
    }

    private fun onSuccess() {
        startActivity(divisionsIntent())
        finish()
    }

    private fun onError() {
        login.error = resources.getString(R.string.error_invalid_password)
        requestFocusToLogin()
        progress.visibility = View.INVISIBLE
        loginButton.startAnimation(fadeAnimation(true))
    }

    override fun onConnectionTimeout() {
        val msg = mHandler.obtainMessage()
        msg.sendToTarget()
    }

    @OnClick(R.id.loginButton, R.id.password, R.id.forgotPassword)
    override fun onClick(v: View?) {
        login.error = null
        when (v?.id) {
            R.id.loginButton -> {
                if (isValidInput()) {
                    viewModel.updateAuthData(login.text.toString(), password.text.toString())
                    viewModel.startRefresh()
                }
            }
            R.id.forgotPassword -> Logger.debug("Forgot pass clicked")
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

        animation.duration = 250
        animation.isFillEnabled = true
        animation.fillAfter = true
        return animation
    }
}

