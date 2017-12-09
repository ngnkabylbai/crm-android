package kz.mycrm.android.ui.main.notification

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.R
import kz.mycrm.android.ui.login.loginIntent
import kz.mycrm.android.ui.main.MainViewModel

/**
 * Created by lab on 11/25/17.
 */
class NotificationFragment: Fragment() {
    private lateinit var notificationViewModel: NotificationViewModel
    private lateinit var sharedViewModel: MainViewModel

    lateinit var rvCurrentNotifications: RecyclerView
    lateinit var rvPastNotifications: RecyclerView

    private lateinit var currentNotificationAdapter : CurrentNotificationAdapter
    private lateinit var pastNotificationAdapter : PastNotificationAdapter
    private lateinit var lm : LinearLayoutManager

    @BindView(R.id.logout)
    lateinit var logoutButton: Button

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View = inflater!!.inflate(R.layout.fragment_notification, container, false)
        return view
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