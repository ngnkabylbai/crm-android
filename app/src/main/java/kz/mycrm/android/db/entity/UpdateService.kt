package kz.mycrm.android.db.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nurbek Kabylbay on 19.02.2018.
 */
class UpdateService {

    @SerializedName("division_service_id")
    @Expose
    lateinit var id: String

    @SerializedName("duration")
    @Expose
    var duration: Int = 0

    @SerializedName("price")
    @Expose
    var price: String? = null

    @SerializedName("quantity")
    @Expose
    var quantity: Int = 1

    @SerializedName("discount")
    @Expose
    var discount: Int = 0

}