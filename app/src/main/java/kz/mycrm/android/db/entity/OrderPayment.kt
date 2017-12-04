package kz.mycrm.android.db.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */

class OrderPayment {

    @SerializedName("value")
    @Expose
    private lateinit var value: String

    @SerializedName("payment_id")
    @Expose
    private lateinit var paymentId: String

    @SerializedName("order_id")
    @Expose
    private lateinit var orderId: String
}