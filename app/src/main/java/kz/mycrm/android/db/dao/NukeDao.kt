package kz.mycrm.android.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

/**
 * Created by NKabylbay on 12/9/2017.
 */
@Dao
interface NukeDao {

    @Query("DELETE FROM token")
    fun nukeToken()

    @Query("DELETE FROM division")
    fun nukeDivision()

    @Query("DELETE FROM mOrder")
    fun nukeOrder()

}