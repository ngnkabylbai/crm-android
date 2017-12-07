package kz.mycrm.android.db.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */

class Customer {

    @SerializedName("id")
    @Expose
    private lateinit var id: String

    @SerializedName("source_id_title")
    @Expose
    private lateinit var sourceIdTitle: String

    @SerializedName("phone")
    @Expose
    private lateinit var phone: String

    @SerializedName("canceled_orders")
    @Expose
    private lateinit var canceledOrders: String

    @SerializedName("deposit")
    @Expose
    private lateinit var deposit: String

    @SerializedName("debt")
    @Expose
    private lateinit var debt: String

    @SerializedName("source_id")
    @Expose
    private lateinit var sourceId: String

    @SerializedName("sms_birthday")
    @Expose
    private lateinit var smsBirthday: String

    @SerializedName("sms_exclude")
    @Expose
    private lateinit var smsExclude: String

    @SerializedName("employer")
    @Expose
    private lateinit var employer: String

    @SerializedName("job")
    @Expose
    private lateinit var job: String

    @SerializedName("lastname")
    @Expose
    private lateinit var lastname: String

    @SerializedName("discount")
    @Expose
    private lateinit var discount: String

    @SerializedName("city")
    @Expose
    private lateinit var city: String

    @SerializedName("balance")
    @Expose
    private lateinit var balance: String

    @SerializedName("lastVisit")
    @Expose
    private lateinit var lastVisit: String

    @SerializedName("address")
    @Expose
    private lateinit var address: String

    @SerializedName("smsExcludeTitle")
    @Expose
    private lateinit var smsExcludeTitle: String

    @SerializedName("smsBirthdayTitle")
    @Expose
    private lateinit var smsBirthdayTitle: String

    @SerializedName("name")
    @Expose
    private lateinit var name: String

    @SerializedName("categoriesTitle")
    @Expose
    private lateinit var categoriesTitle: String

    @SerializedName("finishedOrders")
    @Expose
    private lateinit var finishedOrders: String

    @SerializedName("categories")
    @Expose
    private lateinit var categories: String

    @SerializedName("comments")
    @Expose
    private lateinit var comments: String

    @SerializedName("revenue")
    @Expose
    private lateinit var revenue: String
}