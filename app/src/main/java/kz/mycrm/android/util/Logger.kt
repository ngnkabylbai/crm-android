package kz.mycrm.android.util

import android.util.Log

/**
 * Created by NKabylbay on 11/18/2017.
 */
object Logger {

    fun debug(msg: Any) {
        Log.d("DBG", "$msg")
    }

    fun api(msg: Any) {
        Log.d("API", "$msg")
    }
}