package kz.mycrm.android.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.roughike.bottombar.BottomBar
import com.roughike.bottombar.OnTabSelectListener
import kz.mycrm.android.R
import kz.mycrm.android.application.MycrmApp
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.ui.division.DivisionFragment
import kz.mycrm.android.ui.login.LoginActivity
import kz.mycrm.android.ui.login.LoginViewModel
import kz.mycrm.android.ui.main.journal.JournalFragment
import kz.mycrm.android.util.Logger


fun Context.mainIntent(): Intent {
    return Intent(this, MainActivity::class.java)
}

class MainActivity : AppCompatActivity() {

    @BindView(R.id.btmBar)
    lateinit var bottomBar :BottomBar

    private lateinit var viewModel: MainViewModel

    private lateinit var journalFragment: JournalFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        journalFragment = JournalFragment()

        bottomBar.setOnTabSelectListener { tabId ->
            val fragmentTransaction :FragmentTransaction = supportFragmentManager.beginTransaction()

            val fragment :Fragment = setFragment(tabId)
            fragmentTransaction.replace(R.id.fragment, fragment)
            fragmentTransaction.commit()
        }
    }

//    private fun requestDivisions(token: String, expand: String?) {
//        Logger.debug("Requesting division lists...")
//        viewModel.requestUserDivisions(token, expand)
//                .observe(this, Observer {divisions ->
//                    //                TODO:
//                })
//    }

    private fun setFragment(tabId :Int) :Fragment {
        return when(tabId) {
                    1 -> {
                        journalFragment
                    }
                    else -> {
                        DivisionFragment()
                    }
                }
    }


}
