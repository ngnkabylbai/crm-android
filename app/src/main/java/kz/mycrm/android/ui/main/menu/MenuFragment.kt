package kz.mycrm.android.ui.main.menu

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_menu.*
import kz.mycrm.android.R
import kz.mycrm.android.ui.login.loginIntent

/**
 * Created by NKabylbay on 12/9/2017.
 */
class MenuFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout.setOnClickListener {
            activity!!.startActivity(context!!.loginIntent())
            activity!!.finish()
        }
    }
}