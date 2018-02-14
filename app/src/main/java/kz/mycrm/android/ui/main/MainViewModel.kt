package kz.mycrm.android.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.AppVersion
import kz.mycrm.android.repository.AppInfoRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by lab on 11/20/17.
 */
class MainViewModel :ViewModel() {

    private val appInfoRepository: AppInfoRepository = AppInfoRepository(AppExecutors)
    private var appVersion: LiveData<Resource<AppVersion>>

    private val checkAppVersion = MutableLiveData<Boolean>()

    init {
        appVersion = Transformations.switchMap(checkAppVersion) { _ -> requestAppVersion()}

    }

    fun getAppVersion() : LiveData<Resource<AppVersion>> {
        return appVersion
    }

    fun checkAppVersion() {
        this.checkAppVersion.value = null
    }

    private fun requestAppVersion(): LiveData<Resource<AppVersion>> {
        return appInfoRepository.requestAppVersion()
    }
}
