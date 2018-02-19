package kz.mycrm.android.db.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nurbek Kabylbay on 19.02.2018.
 */
class UpdateOrder {

    @SerializedName("staff_id")
    @Expose
    var staffId: String? = null

    @SerializedName("services")
    @Expose
    lateinit var services: List<UpdateService>
}