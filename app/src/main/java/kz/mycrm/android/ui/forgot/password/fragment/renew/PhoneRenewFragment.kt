package kz.mycrm.android.ui.forgot.password.fragment.renew

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.ViewSwitcher
import kotlinx.android.synthetic.main.fragment_forgot_password_renew.*
import kz.mycrm.android.R
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Status

/**
 * Created by Nurbek Kabylbay on 31.01.2018.
 */
class PhoneRenewFragment: Fragment() {

    private lateinit var code: String
    private lateinit var phone: String

    private lateinit var viewModel: RenewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forgot_password_renew, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(RenewViewModel::class.java)
        viewModel.getNewToken().observe(this, Observer { newToken ->
            when(newToken!!.status) {
                Status.LOADING -> startProgress()
                Status.SUCCESS -> onSuccess(newToken.data)
                Status.ERROR -> onError()
            }
        })

        loginButtonTextSwitcher.inAnimation = AnimationUtils.loadAnimation(activity, android.R.anim.fade_in)
        loginButtonTextSwitcher.setFactory(mFactory)
        loginButtonTextSwitcher.setCurrentText(getString(R.string.action_approve))

        loginButton.setOnClickListener {
            val password = passwordEditText.text.toString()
            val reEnteredPassword = reEnterPasswordEditText.text.toString()

            if (!password.isEmpty() && !reEnteredPassword.isEmpty() && password == reEnteredPassword
                                                                    && password.length >= 6) {
                viewModel.requestRenewPassword(phone, password, code)

            } else if (!password.isEmpty() && !reEnteredPassword.isEmpty() && password == reEnteredPassword
                                                                            && password.length < 6) {
                setPasswordEditTextError("Значение должно содержать минимум 6 символов")

            } else if (!password.isEmpty() && !reEnteredPassword.isEmpty() && password != reEnteredPassword) {

                setPasswordEditTextError("Пароли не совпадают")

            } else if (password.isEmpty() || reEnteredPassword.isEmpty()) {

                setPasswordEditTextError("Заполните все поля")
            }
        }
    }

    private fun startProgress() {
        loginButtonTextSwitcher.setCurrentText(getString(R.string.empty_string))
        loginProgressBar.visibility = View.VISIBLE
    }

    private fun onSuccess(newToken: Token?) {
        Logger.debug("Received new token:" + (newToken?.token ?: "null"))
        activity!!.finish()
        endProgress()
    }

    private fun onError() {
        setPasswordEditTextError("Ошибка при запросе")
        endProgress()
    }

    private fun setPasswordEditTextError(error: String) {
        passwordEditText.error = error
        passwordEditText.requestFocus()
    }

    private fun endProgress() {
        loginProgressBar.visibility = View.GONE
        loginButtonTextSwitcher.setText(getString(R.string.action_save_and_login))
    }

    fun setCode(code: String) {
        this.code = code
    }

    fun setPhone(phone: String) {
        this.phone = phone
    }

    private val mFactory = object : ViewSwitcher.ViewFactory {
        override fun makeView(): View {
            val view = TextView(activity)
            view.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            view.setTextColor(ContextCompat.getColor((context as Context), R.color.white))
            return view
        }
    }
}