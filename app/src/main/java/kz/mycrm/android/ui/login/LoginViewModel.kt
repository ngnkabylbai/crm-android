package kz.mycrm.android.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.repository.DivisionRepository
import kz.mycrm.android.repository.JournalRepository
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by NKabylbay on 11/11/2017.
 */
class LoginViewModel : ViewModel() {

    private var tokenRepository = TokenRepository(AppExecutors)
    private var divisionRepository = DivisionRepository(AppExecutors)
    private var journalRepository = JournalRepository(AppExecutors)

    private val token: LiveData<Resource<Token>>
    private lateinit var phone: String
    private lateinit var password: String

    private val toRequest = MutableLiveData<Boolean>()

    init {
        tokenRepository.nukeTables()
        divisionRepository.nukeTables()
        journalRepository.nukeTables()
        token = Transformations.switchMap(toRequest) { _ -> tokenRepository.requestToken(phone, password)}
    }

    fun startRefresh() {
        toRequest.value = null
    }

    fun requestToken(): LiveData<Resource<Token>> {
        return token
    }

    fun updateAuthData(newPhone: String, newPassword: String) {
        this.password = newPassword
        this.phone = newPhone.replace('-', ' ')
    }
}