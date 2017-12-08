package kz.mycrm.android.ui.main.division

import android.arch.lifecycle.*
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.repository.UserRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Logger
import kz.mycrm.android.util.Resource

/**
 * Created by lab on 11/18/17.
 */
class DivisionViewModel : ViewModel(){

    private val tokenRepository: TokenRepository = TokenRepository(AppExecutors)
    private var userRepository = UserRepository(AppExecutors)

    fun getToken(): LiveData<Token> {
        Logger.debug("getToken")
        return tokenRepository.getToken()
    }

    fun loadUserDivisions(accessToken:String): LiveData<Resource<List<Division>>> {
        Logger.debug("loadUserDivisions")
        return userRepository.getUserDivisions(accessToken)
    }
}