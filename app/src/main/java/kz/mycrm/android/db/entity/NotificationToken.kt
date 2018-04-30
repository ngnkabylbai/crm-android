package kz.mycrm.android.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Nurbek Kabylbay on 12.02.2018.
 */
@Entity
class NotificationToken() {

    @PrimaryKey
    var id:Int = 0

    lateinit var notificationToken: String

    constructor(notificationToken: String) : this() {
        this.notificationToken = notificationToken
    }
}