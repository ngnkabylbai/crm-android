package kz.mycrm.android.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import kz.mycrm.android.db.entity.NotificationToken

/**
 * Created by Nurbek Kabylbay on 12.02.2018.
 */
@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotificationToken(token: NotificationToken)

    @Query("SELECT * FROM NotificationToken LIMIT 1")
    fun getNotificationToken(): NotificationToken

    @Query("DELETE FROM NotificationToken")
    fun nukeNotificationToken()

}