package kz.mycrm.android.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import kz.mycrm.android.db.entity.Division
import kz.mycrm.android.db.entity.Token

/**
 * Created by lab on 11/20/17.
 */
@Dao
interface DivisionDao {

    @Query("SELECT * FROM division")
    fun getDivisionsList(): LiveData<List<Division>>

    @Query("SELECT * FROM division WHERE id = :arg0")
    fun getDivisionById(id: Int): LiveData<Division>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDivisionsList(division: List<Division>)
}