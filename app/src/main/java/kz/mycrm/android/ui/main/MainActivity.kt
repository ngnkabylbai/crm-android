package kz.mycrm.android.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import kz.mycrm.android.R
import kz.mycrm.android.ui.notification.NotificationFragment


fun Context.mainIntent(): Intent {
    return Intent(this, MainActivity::class.java)
}

class MainActivity : AppCompatActivity() {

    @BindView(R.id.bottom_navigation)
    lateinit var bottomBar :AHBottomNavigation

    private lateinit var viewModel: MainViewModel

    lateinit var items: List<AHBottomNavigationItem>

    lateinit var fragment :Fragment
    lateinit var fragmentTransaction:FragmentTransaction


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        items = arrayListOf(AHBottomNavigationItem(resources.getString(R.string.navigation_bar_calendar), R.mipmap.ic_nav_calendar),
                AHBottomNavigationItem(resources.getString(R.string.navigation_bar_client), R.mipmap.ic_nav_clients),
                AHBottomNavigationItem(resources.getString(R.string.navigation_bar_notification), R.mipmap.ic_nav_notifications),
                AHBottomNavigationItem(resources.getString(R.string.navigation_bar_menu), R.mipmap.ic_nav_menu)
        )

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        bottomBar.addItems(items)
        bottomBar.titleState = AHBottomNavigation.TitleState.ALWAYS_HIDE
        bottomBar.setColoredModeColors(Color.GRAY, Color.DKGRAY)

        fragment = NotificationFragment()
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, fragment)
        fragmentTransaction.commit()

        bottomBar.setOnTabSelectedListener { tabId, wasSelected ->
            fragment = setFragment(tabId)
            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment, fragment)
            fragmentTransaction.commit()
            true
        }
    }

    private fun setFragment(tabId :Int) :Fragment {

        when(tabId){
            0->fragment = NotificationFragment()
            2->fragment = NotificationFragment()
        }
        return fragment
    }
}
