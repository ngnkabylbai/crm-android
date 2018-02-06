package kz.mycrm.android.ui.forgot.password.fragment.renew

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.repository.PasswordRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by Nurbek Kabylbay on 05.02.2018.
 */
class RenewViewModel: ViewModel() {

    private val passwordRepository = PasswordRepository(AppExecutors)

    private val token: LiveData<Resource<Token>>
    private val toRequest = MutableLiveData<Boolean>()

    private lateinit var phone: String
    private lateinit var password: String
    private lateinit var code: String

    init {
        token = Transformations.switchMap(toRequest) { _ -> passwordRepository.renewPassword(phone, password, code)}
    }

    fun getNewToken(): LiveData<Resource<Token>> {
        return token
    }

    fun requestRenewPassword(phone: String, password: String, code: String) {
        this.phone = phone.replace('-', ' ')
        this.password = password
        this.code = code
        this.toRequest.value = null
    }
}