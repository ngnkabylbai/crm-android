package kz.mycrm.android

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.util.AppExecutors

/**
 * Created by NKabylbay on 11/11/2017.
 */
class SplashViewModel : ViewModel() {

    private val tokenRepository: TokenRepository = TokenRepository(AppExecutors)

    fun isAuthenticated(): LiveData<Token> {
        return tokenRepository.getToken()
    }
}