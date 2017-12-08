package kz.mycrm.android.ui.main.journal

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.StaffJournal
import kz.mycrm.android.db.entity.Token
import kz.mycrm.android.repository.JournalRepository
import kz.mycrm.android.repository.TokenRepository
import kz.mycrm.android.repository.UserRepository
import kz.mycrm.android.util.AppExecutors
import kz.mycrm.android.util.Resource

/**
 * Created by Nurbek Kabylbay on 23.11.2017.
 */
class JournalViewModel: ViewModel() {

    private var journalRepository = JournalRepository(AppExecutors)
    private var tokenRepository = TokenRepository(AppExecutors)
    private var userRepository = UserRepository(AppExecutors)

    fun requestJournal(token: String, date: String,id: Int, staff: IntArray): LiveData<Resource<StaffJournal>> {
        return journalRepository.requestJournal(token, date, id, staff)
    }

    fun getToken(): LiveData<Token> {
        return tokenRepository.getToken()
    }

    fun getDivisions(): LiveData<List<Division>> {
        return userRepository.getDivisions()
    }
}