package kz.mycrm.android.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

/**
 * Created by Nurbek Kabylbay on 08.02.2018.
 */
@Dao
interface DummyObjectDao {

    @Query("SELECT * FROM DummyString LIMIT 1")
    fun getDummyString(): LiveData<Array<String>>

}