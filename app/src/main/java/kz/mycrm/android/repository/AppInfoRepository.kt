package kz.mycrm.android.repository

import android.arch.lifecycle.LiveData
import kz.mycrm.android.MycrmApp
import kz.mycrm.android.api.ApiResponse
import kz.mycrm.android.db.entity.AppVersion
import kz.mycrm.android.util.ApiUtils
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource

/**
 * Created by Nurbek Kabylbay on 06.02.2018.
 */
class AppInfoRepository(private val appExecutors: AppExecutors) {


    fun requestAppVersion(): LiveData<Resource<AppVersion>> {
        return object : NetworkBoundResource<AppVersion, AppVersion>(appExecutors) {
            override fun saveCallResult(item: AppVersion) {
                MycrmApp.database.AppInfoDao().insertAppVersionInfo(item)
            }

            override fun shouldFetch(data: AppVersion?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<AppVersion> {
                return MycrmApp.database.AppInfoDao().getAppVersion()
            }

            override fun createCall(): LiveData<ApiResponse<AppVersion>> {
                return ApiUtils.getAppVersionService().requestAppVersion()
            }
        }.asLiveData()
    }
}