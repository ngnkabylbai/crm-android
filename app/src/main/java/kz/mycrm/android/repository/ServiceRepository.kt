package kz.mycrm.android.repository

import kz.mycrm.android.MycrmApp
import kz.mycrm.android.db.entity.Service

/**
 * Created by Nurbek Kabylbay on 14.02.2018.
 */
class ServiceRepository {

    fun getServiceById(id: String): Service {
        return MycrmApp.database.ServiceDao().getServiceById(id)
    }
}