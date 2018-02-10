package kz.mycrm.android.ui.forgot.password.fragment.enter

import kz.mycrm.android.repository.PasswordRepository
import kz.mycrm.android.util.AppExecutors

/**
 * Created by Nurbek Kabylbay on 05.02.2018.
 */
class PhoneEnterViewModel {

    private var smsCodeRepository = PasswordRepository(AppExecutors)

    fun requestSmsCode(phone: String) {
        val formattedPhone = phone.replace('-', ' ')
        smsCodeRepository.requestSmsCode(formattedPhone)
    }
}