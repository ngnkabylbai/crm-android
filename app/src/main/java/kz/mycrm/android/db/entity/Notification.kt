package kz.mycrm.android.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Nurbek Kabylbay on 19.02.2018.
 */
@Entity
class Notification {

    @PrimaryKey
    lateinit var orderId: String

    var title: String? = null

    var divisionId: String? = null

    var staffId: String? = null

    var body: String? = null

    var datetime: Long = 0

}