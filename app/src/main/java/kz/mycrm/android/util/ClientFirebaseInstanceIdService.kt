package kz.mycrm.android.util

import android.content.Context
import androidx.content.edit
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import kz.mycrm.android.R

/**
 * Created by Nurbek Kabylbay on 03.02.2018.
 */
class ClientFirebaseInstanceIdService : FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Logger.debug("ClientFirebaseInstanceIdService: New FCM token $refreshedToken")

        val sharedPref = getSharedPreferences(getString(R.string.app_mycrm_shared_key), Context.MODE_PRIVATE)
        sharedPref.edit {
            putString(getString(R.string.app_mycrm_notification_token), refreshedToken)
        }
    }
}