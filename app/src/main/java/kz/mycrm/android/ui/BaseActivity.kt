package kz.mycrm.android.ui

import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity

/**
 * Created by Nurbek Kabylbay on 03.12.2017.
 */
open class BaseActivity: AppCompatActivity() {

    internal lateinit var builder: AlertDialog.Builder

    internal fun showMessage(msg: String) {
        builder.setMessage(msg)
        builder.show()
    }
}