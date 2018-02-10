package kz.mycrm.android.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Nurbek Kabylbay on 10.02.2018.
 */
@Entity
class OtpInfo() {

    @PrimaryKey
    lateinit var phoneNumber: String

    var lastSentTime: Long = 0

    constructor(phoneNumber: String, lastSentTime: Long): this() {
        this.phoneNumber = phoneNumber
        this.lastSentTime = lastSentTime
    }
}
