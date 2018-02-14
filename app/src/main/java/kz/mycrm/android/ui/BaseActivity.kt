package kz.mycrm.android.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import kz.mycrm.android.util.Constants

/**
 * Created by Nurbek Kabylbay on 03.12.2017.
 */
open class BaseActivity: AppCompatActivity() {

    internal lateinit var builder: AlertDialog.Builder

    internal fun showMessage(msg: String) {
        builder.setMessage(msg)
        builder.show()
    }

    fun redirectToMarket() {
        val intent = Intent(Intent.ACTION_VIEW)
//        intent.data = Uri.parse("market://details?id=kz.mycrm.android") TODO: change
        intent.data = Uri.parse("market://details?id=com.dropbox.android")
        startActivityForResult(intent, Constants.marketRequestCode)
    }

    fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}