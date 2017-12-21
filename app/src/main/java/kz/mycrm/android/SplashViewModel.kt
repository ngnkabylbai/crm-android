package kz.mycrm.android

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.util.AppExecutors

/**
 * Created by NKabylbay on 11/11/2017.
 */
class SplashViewModel : ViewModel() {

    private val tokenRepository: TokenRepository = TokenRepository(AppExecutors)

    private val tokenLiveData: LiveData<Token> = tokenRepository.getToken()
    private var isAuthenticatedLiveData: LiveData<Boolean>

    init {
        isAuthenticatedLiveData = Transformations.map(tokenLiveData) { token -> token != null }
    }

    fun checkAuthentication(): LiveData<Boolean> {
        return isAuthenticatedLiveData
    }
}