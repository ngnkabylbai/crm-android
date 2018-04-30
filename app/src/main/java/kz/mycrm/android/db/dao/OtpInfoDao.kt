package kz.mycrm.android.db.dao

import android.arch.persistence.room.*
import kz.mycrm.android.db.entity.OtpInfo

/**
 * Created by Nurbek Kabylbay on 10.02.2018.
 */
@Dao
interface OtpInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOtpInfo(otpInfo: OtpInfo)

    @Query("SELECT * from OtpInfo WHERE phoneNumber = :arg0")
    fun getLastInfo(phone: String): OtpInfo?

}
