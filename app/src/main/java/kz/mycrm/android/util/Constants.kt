package kz.mycrm.android.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Nurbek Kabylbay on 16.01.2018.
 */
object Constants {

    val orderDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    val orderStatusEnabled = 0
    val orderStatusDisabled = 1
    val orderStatusFinished = 3
    val orderStatusCanceled = 4
    val orderStatusWaiting = 5

    val millisToRefreshOtp = 90000L

    val introRequestCode = 1
    val marketRequestCode = 2
    val infoRequestCode = 3
    val loginRequestCode = 4

    val defaultChannelId = "mycrm_notifications"

}
