package kz.mycrm.android.util

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by Nurbek Kabylbay on 03.02.2018.
 */
class ClientFirebaseInstanceIdService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {

        val refreshedToken = FirebaseInstanceId.getInstance().token
        Logger.debug("Refreshed FCM token: $refreshedToken")

//        TODO: realize
//        sendRegistrationToServer(refreshedToken)
    }
}