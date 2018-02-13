package kz.mycrm.android.util

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import kz.mycrm.android.repository.NotificationRepository

/**
 * Created by Nurbek Kabylbay on 03.02.2018.
 */
class ClientFirebaseInstanceIdService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Logger.debug("Refreshed FCM token: $refreshedToken")

        val notificationRepository = NotificationRepository(null)
        notificationRepository.updateNotificationToken(refreshedToken!!)
    }
}