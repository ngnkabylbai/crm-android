package kz.mycrm.android.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */

@Entity
class Customer {

    @PrimaryKey
    @ColumnInfo(name = "cus_id")
    @SerializedName("id")
    @Expose
    lateinit var id: String

    @ColumnInfo(name = "source_id_title")
    @SerializedName("source_id_title")
    @Expose
    lateinit var sourceIdTitle: String

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    @Expose
    lateinit var phone: String

    @ColumnInfo(name = "canceled_orders")
    @SerializedName("canceled_orders")
    @Expose
    lateinit var canceledOrders: String

    @ColumnInfo(name = "deposit")
    @SerializedName("deposit")
    @Expose
    lateinit var deposit: String

    @ColumnInfo(name = "debt")
    @SerializedName("debt")
    @Expose
    lateinit var debt: String

    @ColumnInfo(name = "source_id")
    @SerializedName("source_id")
    @Expose
    lateinit var sourceId: String

    @ColumnInfo(name = "sms_birthday")
    @SerializedName("sms_birthday")
    @Expose
    lateinit var smsBirthday: String

    @ColumnInfo(name = "sms_exclude")
    @SerializedName("sms_exclude")
    @Expose
    lateinit var smsExclude: String

    @ColumnInfo(name = "employer")
    @SerializedName("employer")
    @Expose
    lateinit var employer: String

    @ColumnInfo(name = "job")
    @SerializedName("job")
    @Expose
    lateinit var job: String

    @ColumnInfo(name = "lastname")
    @SerializedName("lastname")
    @Expose
    lateinit var lastname: String

    @ColumnInfo(name = "discount")
    @SerializedName("discount")
    @Expose
    lateinit var discount: String

    @ColumnInfo(name = "city")
    @SerializedName("city")
    @Expose
    lateinit var city: String

    @ColumnInfo(name = "balance")
    @SerializedName("balance")
    @Expose
    lateinit var balance: String

    @ColumnInfo(name = "lastVisit")
    @SerializedName("lastVisit")
    @Expose
    lateinit var lastVisit: String

    @ColumnInfo(name = "address")
    @SerializedName("address")
    @Expose
    lateinit var address: String

    @ColumnInfo(name = "smsExcludeTitle")
    @SerializedName("smsExcludeTitle")
    @Expose
    lateinit var smsExcludeTitle: String

    @ColumnInfo(name = "smsBirthdayTitle")
    @SerializedName("smsBirthdayTitle")
    @Expose
    lateinit var smsBirthdayTitle: String

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    lateinit var name: String

    @ColumnInfo(name = "categoriesTitle")
    @SerializedName("categoriesTitle")
    @Expose
    lateinit var categoriesTitle: String

    @ColumnInfo(name = "finishedOrders")
    @SerializedName("finishedOrders")
    @Expose
    lateinit var finishedOrders: String

    @ColumnInfo(name = "categories")
    @SerializedName("categories")
    @Expose
    lateinit var categories: String

    @ColumnInfo(name = "comments")
    @SerializedName("comments")
    @Expose
    lateinit var comments: String

    @ColumnInfo(name = "revenue")
    @SerializedName("revenue")
    @Expose
    lateinit var revenue: String
}