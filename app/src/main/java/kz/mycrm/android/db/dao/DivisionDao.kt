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
    fun getDivisions(): LiveData<List<Division>>

    @Query("SELECT * FROM division WHERE id = :arg0")
    fun getDivisionById(id: Int): LiveData<Division>

    @Query("SELECT COUNT(*) FROM division")
    fun getCount() : Int

    @Query("SELECT name FROM division")
    fun getItem() : Int

    @Insert
    fun insertDivision (division: List<Division>)

    @Update
    fun updateDivision (division: List<Division>)

    @Delete
    fun deleteDivision (division: List<Division>)
}