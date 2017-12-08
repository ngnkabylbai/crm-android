package kz.mycrm.android.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import kz.mycrm.android.db.entity.Service

/**
 * Created by asset on 12/8/17.
 */
@Dao
interface ServiceDao {

    @Insert
    fun insertService(service: Service)

    @Update
    fun updateService(service: Service)

    @Query("SELECT * FROM service LIMIT 1")
    fun getService(): LiveData<Service>
}