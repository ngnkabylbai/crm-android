package kz.mycrm.android.ui.main.menu

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.R
import kz.mycrm.android.ui.login.loginIntent

/**
 * Created by NKabylbay on 12/9/2017.
 */
class MenuFragment : Fragment() {


    @BindView(R.id.logout)
    lateinit var logoutButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButterKnife.bind(this, view)

        logoutButton.setOnClickListener {
            nukeTables()
            activity.finish()
            activity.startActivity(activity.loginIntent())
        }
    }


    private fun nukeTables() {
        MycrmApp.database.NukeDao().nukeToken()
        MycrmApp.database.NukeDao().nukeOrder()
        MycrmApp.database.NukeDao().nukeDivision()
    }
}