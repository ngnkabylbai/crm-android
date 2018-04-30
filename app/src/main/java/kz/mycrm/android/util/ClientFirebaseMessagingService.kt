package kz.mycrm.android.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kz.mycrm.android.R
import kz.mycrm.android.SplashActivity
import kz.mycrm.android.ui.main.division.DivisionsActivity


/**
 * Created by Nurbek Kabylbay on 03.02.2018.
 */
class ClientFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Logger.debug("onMessageReceived()")

        generateNotification(remoteMessage)

    }

    private fun generateNotification(remoteMessage: RemoteMessage) {
        val vibration = LongArray(2, {1000L})
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val resultIntent = Intent(this, SplashActivity::class.java)
        resultIntent.putExtra("staff_id", remoteMessage.data["staff_id"])
        resultIntent.putExtra("division_id", remoteMessage.data["division_id"])
        resultIntent.putExtra("order_id", remoteMessage.data["order_id"])

        val mBuilder = NotificationCompat.Builder(this, Constants.defaultChannelId)
                .setSmallIcon(R.mipmap.ic_logo)
                .setContentTitle(remoteMessage.data["title"])
                .setContentText(remoteMessage.data["body"])
                .setVibrate(vibration)
                .setSound(soundUri)
                .setAutoCancel(true)

        val stackBuilder = TaskStackBuilder.create(this)
                .addParentStack(DivisionsActivity::class.java)
                .addNextIntent(resultIntent)

        val resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        mBuilder.setContentIntent(resultPendingIntent)

        val uniqueId = (System.currentTimeMillis() and 0xff).toInt()

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(uniqueId, mBuilder.build())

    }
}