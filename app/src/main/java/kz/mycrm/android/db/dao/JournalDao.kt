package kz.mycrm.android.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import kz.mycrm.android.db.entity.StaffJournal

/**
 * Created by Nurbek Kabylbay on 06.12.2017.
 */
@Dao
interface JournalDao {

    @Insert
    fun insertJournal(journal: StaffJournal)

    @Update
    fun updateJournal(journal: StaffJournal)

    @Query("SELECT * FROM journal LIMIT 1")
    fun getJournal(): LiveData<StaffJournal>
}