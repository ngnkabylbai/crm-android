package kz.mycrm.android

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.SharedPreferences
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.util.AppExecutors

/**
 * Created by NKabylbay on 11/11/2017.
 */
class SplashViewModel : ViewModel() {

    private val tokenRepository: TokenRepository = TokenRepository(AppExecutors)

    private var isAuthenticatedLiveData: LiveData<Boolean>
    private val checkAuthentication = MutableLiveData<Boolean>()

    init {
        isAuthenticatedLiveData = Transformations.map(checkAuthentication) { _ -> tokenRepository.getToken() != null }
    }

    fun checkAuthentication() {
        this.checkAuthentication.value = null
    }

    fun getAuthentication(): LiveData<Boolean> {
        return isAuthenticatedLiveData
    }

    fun wasIntroShown(context: Context, sharedPref: SharedPreferences): Boolean {
        val defaultValue = context.getString(R.string.app_mycrm_shared_intro_default)
        val sharedValue = sharedPref.getString(context.getString(R.string.app_mycrm_shared_intro), defaultValue)
        return sharedValue == defaultValue
    }

    fun saveState(context: Context, sharedPref: SharedPreferences) {
        val editor = sharedPref.edit()
        editor.putString(context.getString(R.string.app_mycrm_shared_intro), context.getString(R.string.app_mycrm_shared_intro_shown))
        editor.apply()
    }
}