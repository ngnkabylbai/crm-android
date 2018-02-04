package kz.mycrm.android.ui.forgot

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_forgot_main.*
import kz.mycrm.android.R
import kz.mycrm.android.ui.forgot.fragment.PhoneApproveFragment
import kz.mycrm.android.ui.forgot.fragment.PhoneEnterFragment
import kz.mycrm.android.ui.forgot.fragment.PhoneRenewFragment
import kz.mycrm.android.ui.forgot.listener.LoadNextFragmentListener

/**
 * Created by Nurbek Kabylbay on 30.01.2018.
 */

fun Context.forgotPasswordIntent(): Intent {
    return Intent(this, ForgotPassActivity::class.java)
}

class ForgotPassActivity: AppCompatActivity(), LoadNextFragmentListener {

    private val fragmentCount = 3
    private val enterFragmentPosition = 0
    private val approveFragmentPosition = 1

    private lateinit var viewPagerAdapter: ScreenSlidePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_main)

        viewPagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0)
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        else
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
    }


    override fun loadNextFragment() {
        if(viewPager.currentItem == fragmentCount)
            finish()
        else{
            if (viewPager.currentItem == 0) {
                val enterFragment = viewPagerAdapter.getItem(enterFragmentPosition)
                val approveFragment = viewPagerAdapter.getItem(approveFragmentPosition)

                val loginText = (enterFragment as PhoneEnterFragment).getLogin()
                (approveFragment as PhoneApproveFragment).setLogin(loginText)
            }
            viewPager.currentItem = viewPager.currentItem + 1 // load next fragment
        }
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

        private var enterFragment = PhoneEnterFragment()
        private var approveFragment = PhoneApproveFragment()
        private var renewFragment =  PhoneRenewFragment()

        override fun getItem(position: Int): Fragment {
            return when(position) {
                        0 -> enterFragment
                        1 -> approveFragment
                        2 -> renewFragment
                        else -> enterFragment
                    }
        }

        override fun getCount(): Int {
            return fragmentCount
        }
    }
}

//
//class ForgotPassActivity: AppCompatActivity(), View.OnClickListener {
//
//    private var activityState = LoginState.PhoneEnter
//    private var screenHeight = 0
//    private var smallScreen = false
//    private var keyboardIsOpen = false
//
//    private lateinit var timer: CountDownTimer
//
//    private val COUNTDOWN_TIMER_MIL_SECONDS = 60000L
//    private val COUNTDOWN_TIMER_MIL_INTERVAL = 1000L
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_forgot_password)
//
//        val intent = intent
//        screenHeight = intent.getIntExtra("screen_height", 800)
//
//        KeyboardVisibilityEvent.setEventListener(this) { isOpen ->
//            keyboardIsOpen = isOpen
//            if(isOpen)
//                Logger.debug("SoftKeyboard is DOWN")
//            else
//                Logger.debug("SoftKeyboard is UP")
//
//            invalidateLogo()
//        }
//
//        loginButton.setOnClickListener(this)
//
//        loginButtonTextSwitcher.inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
//        loginButtonTextSwitcher.setFactory(mFactory)
//        loginButtonTextSwitcher.setCurrentText(getString(R.string.action_approve))
//
//        timer = object : CountDownTimer(COUNTDOWN_TIMER_MIL_SECONDS, COUNTDOWN_TIMER_MIL_INTERVAL) {
//            override fun onFinish() {
//                Toast.makeText(baseContext, "Timer is done", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onTick(millisUntilFinished: Long) {
//                resendTextSeconds.text = (millisUntilFinished/1000).toString()
//            }
//        }
//
//        setEnterPhoneState()
//    }
//
//    private fun requestFocusToLogin() {
//        loginEditText.requestFocus()
//        loginEditText.setSelection(loginEditText.text.length)
//    }
//
//    private fun isValidInput(): Boolean {
//        if (loginEditText.text.length != 16 || password.text.isEmpty()) {
//            loginEditText.error = getString(R.string.error_empty_string)
//            requestFocusToLogin()
//            return false
//        }
//        return true
//    }
//
//    private fun onLoading() {
//        progress.visibility = View.VISIBLE
//        loginButtonTextSwitcher.setText(getButtonText())
//        activityState = LoginState.Loading
//    }
//
//    private fun onSuccess() {
//        startActivity(divisionsIntent())
//        finish()
//    }
//
//    private fun onError() {
//        loginEditText.error = getString(R.string.error_invalid_password)
//        requestFocusToLogin()
//        progress.visibility = View.GONE
//        loginButtonTextSwitcher.setText(getButtonText())
//    }
//
//    private fun setEnterPhoneState() {
//        activityState = LoginState.PhoneEnter
//        invalidateLogo()
//        hintTextView.startAnimation(getTextAnim(getString(R.string.hint_forgot)))
//        loginButtonTextSwitcher.setText(getButtonText())
//    }
//
//    private fun setApproveState() {
//        activityState = LoginState.ApproveCode
//        timer.start()
//        invalidateLogo()
//        loginButtonTextSwitcher.setText(getButtonText())
//        resendTextLayout.visibility = View.VISIBLE
//        passwordInputLayout.visibility = View.VISIBLE
//    }
//
//    private fun setNewPassState() {
//        activityState = LoginState.NewPass
//        invalidateLogo()
//        loginButtonTextSwitcher.setText(getButtonText())
//        hintTextView.startAnimation(getTextAnim(getString(R.string.hint_approved)))
//        loginLayout.visibility = View.GONE
//        passwordInputLayout.visibility = View.VISIBLE
//        newPasswordInputLayout.visibility = View.VISIBLE
//    }
//
//    override fun onClick(v: View?) {
//        loginEditText.error = null
//        when (activityState) {
//            LoginState.Loading -> return
//            LoginState.PhoneEnter -> {
//                setApproveState()
//            } // send request
//            LoginState.ApproveCode -> {
//                setNewPassState()
//            } // get new password and renew
//            LoginState.NewPass -> {
//                finish()
//            } // login
//        }
//    }
//
//    private fun getTextAnim(text: String): AlphaAnimation {
//        val anim = AlphaAnimation(1.0f, 0.0f)
//        anim.duration = 200
//        anim.repeatCount = 1
//        anim.repeatMode = Animation.REVERSE
//
//        anim.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationEnd(animation: Animation?) { }
//            override fun onAnimationStart(animation: Animation?) { }
//            override fun onAnimationRepeat(animation: Animation?) {
//                hintTextView.text = text
//            }
//        })
//
//        return anim
//    }
//
//    private fun getButtonText(): String {
//        return when (activityState) {
//            LoginState.Loading -> ""
//            LoginState.PhoneEnter -> getString(R.string.action_send)
//            LoginState.ApproveCode -> getString(R.string.action_approve)
//            LoginState.NewPass -> getString(R.string.action_save_and_login)
//        }
//    }
//
//    private fun invalidateLogo() {
////        if(keyboardIsOpen) {
////            if(smallScreen)
////                logo.visibility = View.GONE
////        } else {
////            logo.visibility = View.VISIBLE
////        }
//    }
//
//    private val mFactory = object : ViewSwitcher.ViewFactory {
//
//        override fun makeView(): View {
//            val view = TextView(this@ForgotPassActivity)
//            view.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
//            view.setTextColor(ContextCompat.getColor(this@ForgotPassActivity, R.color.white))
//            return view
//        }
//    }
//
//}