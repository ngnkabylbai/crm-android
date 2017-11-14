package kz.mycrm.android.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.database.entity.Token
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource
import java.util.concurrent.Executor

/**
 * Created by NKabylbay on 11/11/2017.
 */
class LoginViewModel : ViewModel() {

    private var tokenRepository = TokenRepository(AppExecutors)

    fun requestToken(login: String, password: String): LiveData<Resource<Token>> {
        return tokenRepository.requestToken(login, password)
    }

}