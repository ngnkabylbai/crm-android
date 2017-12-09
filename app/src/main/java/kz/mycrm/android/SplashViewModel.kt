package kz.mycrm.android

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.repository.UserRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource

/**
 * Created by NKabylbay on 11/11/2017.
 */
class SplashViewModel : ViewModel() {

    private val tokenRepository: TokenRepository = TokenRepository(AppExecutors)
    private val userRepository: UserRepository = UserRepository(AppExecutors)

    fun checkForToken(): LiveData<Token> {
        return tokenRepository.getToken()
    }

    fun loadUserDivisions(accessToken:String): LiveData<Resource<List<Division>>> {
        Logger.debug("loadUserDivisions")
        return userRepository.requestUserDivisionList(accessToken)
    }
}