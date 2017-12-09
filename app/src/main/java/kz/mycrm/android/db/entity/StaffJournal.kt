package kz.mycrm.android.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */
class StaffJournal {

    @SerializedName("id")
    @Expose
    @ColumnInfo(name = "id")
    lateinit var id: String

    @SerializedName("start")
    @Expose
    @ColumnInfo(name = "start")
    lateinit var start: String

    @SerializedName("orders")
    @Expose
    @Ignore
    lateinit var orders: List<Order>

    @SerializedName("end")
    @Expose
    @ColumnInfo(name = "end")
    lateinit var end: String

}