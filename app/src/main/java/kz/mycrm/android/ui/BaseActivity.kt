package kz.mycrm.android.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import kz.mycrm.android.util.Constants

/**
 * Created by Nurbek Kabylbay on 03.12.2017.
 */
open class BaseActivity: AppCompatActivity() {

    protected lateinit var dialogManager: MaterialDialog.Builder
    private var dialog: MaterialDialog? = null

    internal fun showMessage(msg: String, positive: BaseActivity.()->Unit = {}) {

        dialog = dialogManager
                .content(msg)
                .onPositive({_, _-> positive()})
                .onNegative({dialog, _-> dialog.dismiss()})
                .build()

        dialog!!.show()
    }

    fun redirectToMarket() {
        val intent = Intent(Intent.ACTION_VIEW)

        try {
            intent.data = Uri.parse("market://details?id=kz.mycrm.android")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        startActivityForResult(intent, Constants.marketRequestCode)
    }

    fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }
}