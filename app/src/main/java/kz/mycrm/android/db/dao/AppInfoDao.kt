package kz.mycrm.android.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import kz.mycrm.android.db.entity.AppVersion

/**
 * Created by Nurbek Kabylbay on 06.02.2018.
 */
@Dao
interface AppInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAppVersionInfo(appVersion: AppVersion)

    @Query("SELECT * FROM appVersion LIMIT 1")
    fun getAppVersion(): LiveData<AppVersion>

}