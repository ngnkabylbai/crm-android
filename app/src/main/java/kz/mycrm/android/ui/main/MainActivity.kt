package kz.mycrm.android.ui.main

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.roughike.bottombar.BottomBar
import com.roughike.bottombar.OnTabSelectListener
import kz.mycrm.android.R
import kz.mycrm.android.ui.login.LoginActivity
import kz.mycrm.android.util.Logger


fun Context.mainIntent(): Intent {
    return Intent(this, MainActivity::class.java)
}

class MainActivity : AppCompatActivity() {

    @BindView(R.id.fragment)
    lateinit var fragment :FrameLayout
    @BindView(R.id.btmBar)
    lateinit var bottomBar :BottomBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        bottomBar.setOnTabSelectListener(object :OnTabSelectListener{
            override fun onTabSelected(tabId: Int) {

            }

        });
    }

//    private fun requestDivisions(token: String, expand: String?) {
//        Logger.debug("Requesting division lists...")
//        viewModel.requestUserDivisions(token, expand)
//                .observe(this, Observer {divisions ->
//                    //                TODO:
//                })
//    }

//    private fun setFragment(tabId :Int) :Fragment {
//
//        var fragment :Fragment
//
//        when(tabId){
//
//        }
//
//        return fragment
//    }
}
