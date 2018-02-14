package kz.mycrm.android.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import kz.mycrm.android.db.entity.Service

/**
 * Created by asset on 12/8/17.
 */
@Dao
interface ServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertService(service: Service)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertServiceList(services: List<Service>)

    @Update
    fun updateService(service: Service)

    @Query("SELECT * FROM service WHERE service_id = :arg0")
    fun getServiceById(id: String): Service

    @Query("SELECT * FROM service")
    fun getServices():LiveData<List<Service>>
}