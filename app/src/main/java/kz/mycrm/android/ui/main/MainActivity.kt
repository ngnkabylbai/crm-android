package kz.mycrm.android.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import kz.mycrm.android.R
import kz.mycrm.android.application.MycrmApp
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.ui.division.DivisionFragment
import kz.mycrm.android.ui.login.LoginActivity
import kz.mycrm.android.ui.login.LoginViewModel
import kz.mycrm.android.ui.notification.NotificationFragment
import kz.mycrm.android.util.Logger


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

        items = arrayListOf<AHBottomNavigationItem>(AHBottomNavigationItem(resources.getString(R.string.navigation_bar_calendar), R.drawable.calendar_menu),
                AHBottomNavigationItem(resources.getString(R.string.navigation_bar_client), R.drawable.clients_menu),
                AHBottomNavigationItem(resources.getString(R.string.navigation_bar_notification), R.drawable.notifications_menu),
                AHBottomNavigationItem(resources.getString(R.string.navigation_bar_menu), R.drawable.menu)
        )

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        bottomBar.addItems(items)
        bottomBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE)
        bottomBar.setColoredModeColors(Color.GRAY, Color.DKGRAY)
        disableNavigationItems()

        fragment = DivisionFragment()
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment, fragment)
        fragmentTransaction.commit()

        bottomBar.setOnTabSelectedListener(object : AHBottomNavigation.OnTabSelectedListener {

            override fun onTabSelected(tabId: Int, wasSelected: Boolean):Boolean {
                enableNavigationItems()
                fragment = setFragment(tabId)
                fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment, fragment)
                fragmentTransaction.commit()
                return true
            }
        })
    }

    private fun setFragment(tabId :Int) :Fragment {

        when(tabId){
            0->fragment = DivisionFragment()
            2->fragment = NotificationFragment()
        }
        return fragment
    }

    private fun disableNavigationItems() {
        for (i in items.indices - 2 - 3) {
            bottomBar.disableItemAtPosition(i)
        }
    }

    private fun enableNavigationItems() {
        for (i in items.indices) {
            bottomBar.enableItemAtPosition(i)
        }
    }
}
