package kz.mycrm.android.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.util.AppExecutors

/**
 * Created by lab on 11/20/17.
 */
class MainViewModel :ViewModel() {

    private val tokenRepository: TokenRepository = TokenRepository(AppExecutors)

    fun requestTokenFromDB() : LiveData<Token> {
        return tokenRepository.getToken()
    }
}