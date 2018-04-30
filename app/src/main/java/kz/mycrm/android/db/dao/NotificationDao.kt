package kz.mycrm.android.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import kz.mycrm.android.db.entity.Notification
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(token: Notification)

    @Query("SELECT * FROM Notification WHERE staffId = :arg0 ORDER BY datetime")
    fun getAllNotificationsByStaffId(staffId: String): LiveData<List<Notification>>

    @Query("DELETE FROM Notification WHERE datetime(datetime) < datetime('now')")
    fun deleteOldNotifications()

    @Query("DELETE FROM Notification WHERE staffId = :arg0")
    fun deleteStaffNotification(staffId: String)

    @Query("DELETE FROM Notification")
    fun nukeNotification()

}