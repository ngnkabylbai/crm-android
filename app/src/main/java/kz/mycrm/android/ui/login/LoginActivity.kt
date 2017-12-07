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
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.activity_login.*
import kz.mycrm.android.R
import kz.mycrm.android.remote.OnConnectionTimeoutListener
import kz.mycrm.android.remote.RetrofitClient
import kz.mycrm.android.ui.BaseActivity
import kz.mycrm.android.ui.division.divisionsIntent
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
                clearError()
            })
            showMessage(resources.getString(R.string.error_timeout))
        }
    }

    @BindView(R.id.login)
    lateinit var login: EditText
    @BindView(R.id.password)
    lateinit var password: EditText
    @BindView(R.id.progress)
    lateinit var progress: ProgressBar
    @BindView(R.id.loginButton)
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        login.setText("+7 701 381 71 15")
        password.setText("password")

        builder = AlertDialog.Builder(this)
        builder.create()
        RetrofitClient.setConnectionTimeoutListener(this)
    }

    override fun onConnectionTimeout() {
        val msg = mHandler.obtainMessage()
        msg.sendToTarget()
    }

    @OnClick(R.id.loginButton, R.id.password, R.id.forgotPassword)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.loginButton -> {
                clearError()
                if (isValidInput()) {
                    startLoading()
                    viewModel.requestToken(login.text.toString(), password.text.toString())
                            .observe(this, Observer { token ->
                                if (token?.status == Status.SUCCESS) { // success, token received. Go to MainActivity
                                    progress.visibility = View.GONE
                                    startActivity(divisionsIntent())
                                    finish()
                                } else if (token?.status == Status.ERROR) { // error, show error message
                                    password.error = resources.getString(R.string.error_invalid_password)
                                    stopLoading()
                                }
                            })
                }
            }
            R.id.forgotPassword -> Logger.debug("Forgot pass clicked")
        }
    }

    private fun isValidInput(): Boolean {
        var result = true
        if (login.text.isEmpty()) {
            login.error = resources.getString(R.string.error_empty_string)
            result = false
        }
        if (password.text.isEmpty()) {
            password.error = resources.getString(R.string.error_empty_string)
            result = false
        }
        return result
    }

    private fun clearError() {
        progress.visibility = View.GONE
        login.error = null
        password.error = null
    }

    private fun startLoading() {
        progress.visibility = View.VISIBLE
        loginButton.isEnabled = false
    }

    private fun stopLoading() {
        progress.visibility = View.GONE
        loginButton.isEnabled = true
    }
}

