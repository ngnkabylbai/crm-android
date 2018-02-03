package kz.mycrm.android.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.R
import kz.mycrm.android.ui.main.division.DivisionsActivity


/**
 * Created by Nurbek Kabylbay on 03.02.2018.
 */
class ClientFirebaseMessagingService: FirebaseMessagingService() {

    private val defaultChannelId = "mycrm_notifications"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Logger.debug("onMessageReceived()")


        if(!isAuthenticated())
            return

        if (remoteMessage.data.isNotEmpty()) {
            Logger.debug("Message data payload: " + remoteMessage.data)
        }

        if (remoteMessage.notification != null) {
            Logger.debug("Message Notification Body: " + remoteMessage.notification.body)
            val mBuilder = NotificationCompat.Builder(this, defaultChannelId)
                    .setSmallIcon(R.mipmap.ic_logo)
                    .setContentTitle(remoteMessage.notification.title)
                    .setContentText(remoteMessage.notification.body)

            val resultIntent = Intent(this, DivisionsActivity::class.java)
            val stackBuilder = TaskStackBuilder.create(this)
            stackBuilder.addParentStack(DivisionsActivity::class.java)
            stackBuilder.addNextIntent(resultIntent)
            val resultPendingIntent = stackBuilder.getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
            )
            mBuilder.setContentIntent(resultPendingIntent)
            val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.notify(-1, mBuilder.build()) // TODO: add notification ID
        }
    }

    private fun isAuthenticated(): Boolean {
        return MycrmApp.database.TokenDao().getToken() != null
    }
}