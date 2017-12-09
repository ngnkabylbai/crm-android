package kz.mycrm.android.ui.main.journal

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.Order
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

    fun requestJournal(token: String, date: String, divisionId: Int, staffId: IntArray): LiveData<Resource<List<Order>>> {
        return journalRepository.requestJournal(token, date, divisionId, staffId)
    }

    fun getToken(): LiveData<Token> {
        return tokenRepository.getToken()
    }

    fun getDivisions(): LiveData<List<Division>> {
        return userRepository.getDivisionsList()
    }

    fun getDivisionById(id: Int): LiveData<Division> {
        return userRepository.getDivisionById(id)
    }

}