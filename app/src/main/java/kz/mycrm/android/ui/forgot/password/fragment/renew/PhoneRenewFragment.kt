package kz.mycrm.android.ui.forgot.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_forgot_password_renew.*
import kz.mycrm.android.R
import kz.mycrm.android.ui.forgot.ForgotPassActivity
import kz.mycrm.android.ui.forgot.listener.LoadNextFragmentListener

/**
 * Created by Nurbek Kabylbay on 31.01.2018.
 */
class PhoneRenewFragment: Fragment() {

    private var loaderCallback: LoadNextFragmentListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forgot_password_renew, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        loaderCallback = context as ForgotPassActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginButton.setOnClickListener { activity!!.finish() }
    }
}