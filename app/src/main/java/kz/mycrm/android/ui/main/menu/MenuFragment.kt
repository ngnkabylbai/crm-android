package kz.mycrm.android.ui.main.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_menu.*
import kz.mycrm.android.R
import kz.mycrm.android.SplashActivity
import kz.mycrm.android.ui.main.MainActivity
import kz.mycrm.android.ui.main.listener.OnLogoutListener

/**
 * Created by NKabylbay on 12/9/2017.
 */
class MenuFragment : Fragment() {

    private var logoutCallback: OnLogoutListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        logoutCallback = context as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout.setOnClickListener {
            logoutCallback!!.onLogout()
            activity!!.startActivity(Intent(activity, SplashActivity::class.java))
            activity!!.finish()
        }
    }
}
