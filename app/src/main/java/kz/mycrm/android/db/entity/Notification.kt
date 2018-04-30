package kz.mycrm.android.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import kz.mycrm.android.util.TimestampConverter
import java.util.*

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

    @TypeConverters(TimestampConverter::class)
    lateinit var datetime: Date

}