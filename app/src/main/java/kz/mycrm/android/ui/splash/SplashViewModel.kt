package kz.mycrm.android.ui.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.database.entity.Token
import kz.mycrm.android.database.entity.User
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.repository.UserRepository
import kz.mycrm.android.util.Resource

/**
 * Created by NKabylbay on 11/11/2017.
 */
class SplashViewModel : ViewModel() {

    private var userResource: LiveData<Resource<User>>? = null
    private val userRepository: UserRepository = UserRepository()
    private var userToken: Token? = null
    private val tokenRepository: TokenRepository = TokenRepository()

    init {

    }

    fun checkForToken(): LiveData<Boolean> {
        return tokenRepository.hasToken()
    }
}