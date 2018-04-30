package kz.mycrm.android.ui.forgot.password.fragment.approve

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.ViewSwitcher
import com.crashlytics.android.Crashlytics
import kotlinx.android.synthetic.main.fragment_forgot_approve_phone.*
import kz.mycrm.android.R
import kz.mycrm.android.ui.forgot.password.ForgotPassActivity
import kz.mycrm.android.ui.forgot.password.listener.FragmentLifecycle
import kz.mycrm.android.ui.forgot.password.listener.LoadNextFragmentListener
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource
import kz.mycrm.android.util.Status
import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Nurbek Kabylbay on 31.01.2018.
 */
class PhoneApproveFragment: Fragment(), FragmentLifecycle {

    private var loaderCallback: LoadNextFragmentListener? = null
    private lateinit var viewModel: ApproveViewModel
    private var login: String? = null
    private var code: String? = null

    private var timer: CoolDown? = null
    private var isCoolingDown = true

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
                Status.ERROR -> onError(result)
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
                viewModel.requestCodeValidation(login!!, code!!)
            }
        }

        resendTextLayout.setOnClickListener {
            if(!isCoolingDown) {
                viewModel.requestSmsCode(loginEditText.text.toString())
                startTimer()
            }
        }
    }

    private fun onSuccess() {
        loaderCallback?.loadNextFragment(code!!)
        endProgress()
    }

    private fun onError(response: Resource<Array<String>>) {

        try {
            val responseJSON = JSONArray(response.message)[0] as JSONObject
            codeEditText.error = responseJSON.getString("message")
        } catch (e: Exception) {
            Crashlytics.logException(e)
            codeEditText.error = getString(R.string.error)
        }

        codeEditText.requestFocus()
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

    override fun onPauseFragment() {
        timer?.cancel()
    }

    override fun onResumeFragment() {
        startTimer()
    }

    private fun startTimer() {
        if(login != null) {
            val leftMillis = viewModel.requestOtpMillis(login!!)
            timer = CoolDown(leftMillis)
        }
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

    private fun setCoolDown(sec: Int) {
        if(sec==0) {
            resendTextSeconds.visibility = View.GONE
            resendTextSeconds.setTextColor(ContextCompat.getColor(context!!, R.color.login_textColor))
            resendTextHint.setTextColor(ContextCompat.getColor(context!!, R.color.login_textColor))
            isCoolingDown = false
        } else {
            isCoolingDown = true
            resendTextLayout.visibility = View.VISIBLE
            resendTextSeconds.visibility = View.VISIBLE
            resendTextSeconds.setTextColor(ContextCompat.getColor(context!!, R.color.inactive))
            resendTextHint.setTextColor(ContextCompat.getColor(context!!, R.color.inactive))

            val leftTime = " : " + sec.toString()
            resendTextSeconds.text = leftTime
        }
    }


    inner class CoolDown(cd: Long) : CountDownTimer(cd, 1) {

        private var prevSec = 0

        init {
            prevSec = (cd/1000).toInt()
            if(prevSec!=0) {
                Logger.debug("Countdown timer started:" + cd/1000)
                start()
                resendTextLayout.visibility = View.GONE
            }
            setCoolDown(prevSec)
        }

        override fun onTick(millisUntilFinished: Long) {
            val curSec = (millisUntilFinished/1000).toInt()
            if(prevSec>curSec) {
                prevSec = curSec
                setCoolDown(curSec)
            }
        }

        override fun onFinish() {}
    }
}