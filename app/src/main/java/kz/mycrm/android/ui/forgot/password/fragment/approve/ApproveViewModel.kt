package kz.mycrm.android.ui.forgot.password.fragment.approve

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.repository.PasswordRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by Nurbek Kabylbay on 08.02.2018.
 */
class ApproveViewModel: ViewModel() {

    private val passwordRepository = PasswordRepository(AppExecutors)

    private val result: LiveData<Resource<Array<String>>>
    private val toRequest = MutableLiveData<Boolean>()

    private lateinit var phone: String
    private lateinit var code: String

    init {
        result = Transformations.switchMap(toRequest) { _ -> passwordRepository.requestCodeValidation(phone, code)}
    }

    fun getValidationResult(): LiveData<Resource<Array<String>>> {
        return result
    }

    fun requestCodeValidation(phone: String, code: String) {
        this.phone = phone.replace('-', ' ')
        this.code = code
        this.toRequest.value = null
    }
}