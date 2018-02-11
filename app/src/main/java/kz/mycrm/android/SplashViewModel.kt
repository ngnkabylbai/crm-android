package kz.mycrm.android

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.AppVersion
import kz.mycrm.android.repository.AppInfoRepository
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by NKabylbay on 11/11/2017.
 */
class SplashViewModel : ViewModel() {

    private val tokenRepository: TokenRepository = TokenRepository(AppExecutors)
    private val appInfoRepository: AppInfoRepository = AppInfoRepository(AppExecutors)

    private var isAuthenticatedLiveData: LiveData<Boolean>
    private var appVersion: LiveData<Resource<AppVersion>>

    private val checkAuthentication = MutableLiveData<Boolean>()
    private val checkAppVersion = MutableLiveData<Boolean>()

    init {
        isAuthenticatedLiveData = Transformations.map(checkAuthentication) { _ -> tokenRepository.getToken() != null }
        appVersion = Transformations.switchMap(checkAppVersion) { _ -> requestAppVersion()}
    }

    fun checkAuthentication() {
        this.checkAuthentication.value = null
    }

    fun checkAppVersion() {
        this.checkAppVersion.value = null
    }

    fun getAuthentication(): LiveData<Boolean> {
        return isAuthenticatedLiveData
    }

    fun getAppVersion() : LiveData<Resource<AppVersion>> {
        return appVersion
    }

    private fun requestAppVersion(): LiveData<Resource<AppVersion>> {
        return appInfoRepository.requestAppVersion()
    }
}