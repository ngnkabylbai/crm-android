package kz.mycrm.android.ui.forgot.password.fragment.approve

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
import kotlinx.android.synthetic.main.fragment_forgot_approve_phone.*
import kz.mycrm.android.R
import kz.mycrm.android.ui.forgot.password.ForgotPassActivity
import kz.mycrm.android.ui.forgot.password.listener.LoadNextFragmentListener
import kz.mycrm.android.util.Status

/**
 * Created by Nurbek Kabylbay on 31.01.2018.
 */
class PhoneApproveFragment: Fragment() {

    private var loaderCallback: LoadNextFragmentListener? = null
    private lateinit var viewModel: ApproveViewModel
    private lateinit var login: String
    private var code: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forgot_approve_phone, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loaderCallback = context as ForgotPassActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ApproveViewModel::class.java)
        viewModel.getValidationResult().observe(this, Observer { result ->
            when(result?.status) {
                Status.LOADING -> startProgress()
                Status.SUCCESS -> onSuccess()
                Status.ERROR -> onError()
            }
        })

        loginButtonTextSwitcher.inAnimation = AnimationUtils.loadAnimation(activity, android.R.anim.fade_in)
        loginButtonTextSwitcher.setFactory(mFactory)
        loginButtonTextSwitcher.setCurrentText(getString(R.string.action_approve))

        loginButton.setOnClickListener {
            if(codeEditText.text.isEmpty()) {
                codeEditText.error = "Заполните поле"
            } else {
                code = codeEditText.text.toString()
                viewModel.requestCodeValidation(login, code!!)
            }
        }
    }

    private fun onSuccess() {
        loaderCallback?.loadNextFragment(code!!)
        endProgress()
    }

    private fun onError() {
        codeEditText.error = getString(R.string.error_invalid_login_or_code)
        endProgress()
    }

    private fun startProgress() {
        loginButtonTextSwitcher.setCurrentText(getString(R.string.empty_string))
        loginProgressBar.visibility = View.VISIBLE
    }

    private fun endProgress() {
        loginProgressBar.visibility = View.GONE
        loginButtonTextSwitcher.setText(getString(R.string.action_approve))
    }


    fun setLogin(login: String) {
        loginEditText.setText(login)
        this.login = loginEditText.text.toString()
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