package kz.mycrm.android.db.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */
class StaffJournal {

    @SerializedName("id")
    @Expose
    private lateinit var id: String

    @SerializedName("start")
    @Expose
    private lateinit var start: String

    @SerializedName("orders")
    @Expose
    private lateinit var orders: Array<Order>

    @SerializedName("end")
    @Expose
    private lateinit var end: String
}