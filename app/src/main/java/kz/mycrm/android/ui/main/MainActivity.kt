package kz.mycrm.android.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import kz.mycrm.android.R
import kz.mycrm.android.ui.main.journal.JournalFragment
import kz.mycrm.android.ui.main.notification.NotificationFragment


fun Context.mainIntent(): Intent {
    return Intent(this, MainActivity::class.java)
}

class MainActivity : AppCompatActivity() {

    @BindView(R.id.bottom_navigation)
    lateinit var bottomBar :AHBottomNavigation

    private lateinit var viewModel: MainViewModel

    private lateinit var items: List<AHBottomNavigationItem>

    private lateinit var fragment :Fragment
    private lateinit var fragmentTransaction:FragmentTransaction
    private lateinit var journalFragment: JournalFragment
    private lateinit var notificationFragment: NotificationFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        items = arrayListOf(AHBottomNavigationItem(resources.getString(R.string.navigation_bar_calendar), R.mipmap.ic_nav_calendar),
//                AHBottomNavigationItem(resources.getString(R.string.navigation_bar_client), R.mipmap.ic_nav_clients),
                AHBottomNavigationItem(resources.getString(R.string.navigation_bar_notification), R.mipmap.ic_nav_notifications)
//                AHBottomNavigationItem(resources.getString(R.string.navigation_bar_menu), R.mipmap.ic_nav_menu)
        )

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        bottomBar.addItems(items)
        bottomBar.titleState = AHBottomNavigation.TitleState.ALWAYS_HIDE
        bottomBar.accentColor = ContextCompat.getColor(this, R.color.bottom_nav_active)
        bottomBar.inactiveColor = ContextCompat.getColor(this, R.color.bottom_nav_inactive)

        journalFragment = JournalFragment()
            val bundle = Bundle()
            bundle.putInt("division_id", intent.extras.getInt("division_id"))
        journalFragment.arguments = bundle
        notificationFragment = NotificationFragment()

        fragment = notificationFragment
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, fragment)
        fragmentTransaction.commit()

        bottomBar.setOnTabSelectedListener { tabId, _ ->
            fragment = setFragment(tabId)
            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment, fragment)
            fragmentTransaction.commit()
            true
        }
    }

    private fun setFragment(tabId :Int) :Fragment {
        return when(tabId){
            0-> journalFragment
            2-> notificationFragment
            else -> {
                notificationFragment
            }
        }
    }
}
