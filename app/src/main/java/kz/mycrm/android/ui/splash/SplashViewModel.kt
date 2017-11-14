package kz.mycrm.android.ui.splash

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.db.entity.User
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.repository.UserRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by NKabylbay on 11/11/2017.
 */
class SplashViewModel : ViewModel() {

    private var userResource: LiveData<Resource<User>>? = null
    private val userRepository: UserRepository = UserRepository(AppExecutors)
    private val tokenRepository: TokenRepository = TokenRepository(AppExecutors)

    init {

    }

    fun checkForToken(): LiveData<Boolean> {
        return tokenRepository.hasToken()
    }

    fun loadUserDivisions(): LiveData<Resource<Division>>? {
        return userRepository.getUserDivision()
    }
}