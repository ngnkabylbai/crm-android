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
    var sourceIdTitle: String? = null

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @ColumnInfo(name = "canceled_orders")
    @SerializedName("canceled_orders")
    @Expose
    var canceledOrders: String? = null

    @ColumnInfo(name = "deposit")
    @SerializedName("deposit")
    @Expose
    var deposit: String? = null

    @ColumnInfo(name = "debt")
    @SerializedName("debt")
    @Expose
    var debt: String? = null

    @ColumnInfo(name = "source_id")
    @SerializedName("source_id")
    @Expose
    var sourceId: String? = null

    @ColumnInfo(name = "sms_birthday")
    @SerializedName("sms_birthday")
    @Expose
    var smsBirthday: String? = null

    @ColumnInfo(name = "sms_exclude")
    @SerializedName("sms_exclude")
    @Expose
    var smsExclude: String? = null

    @ColumnInfo(name = "employer")
    @SerializedName("employer")
    @Expose
    var employer: String? = null

    @ColumnInfo(name = "job")
    @SerializedName("job")
    @Expose
    var job: String? = null

    @ColumnInfo(name = "lastname")
    @SerializedName("lastname")
    @Expose
    var lastname: String? = null

    @ColumnInfo(name = "discount")
    @SerializedName("discount")
    @Expose
    var discount: String? = null

    @ColumnInfo(name = "city")
    @SerializedName("city")
    @Expose
    var city: String? = null

    @ColumnInfo(name = "balance")
    @SerializedName("balance")
    @Expose
    var balance: String? = null

    @ColumnInfo(name = "lastVisit")
    @SerializedName("lastVisit")
    @Expose
    var lastVisit: String? = null

    @ColumnInfo(name = "address")
    @SerializedName("address")
    @Expose
    var address: String? = null

    @ColumnInfo(name = "smsExcludeTitle")
    @SerializedName("smsExcludeTitle")
    @Expose
    var smsExcludeTitle: String? = null

    @ColumnInfo(name = "smsBirthdayTitle")
    @SerializedName("smsBirthdayTitle")
    @Expose
    var smsBirthdayTitle: String? = null

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    var name: String? = null

    @ColumnInfo(name = "categoriesTitle")
    @SerializedName("categoriesTitle")
    @Expose
    var categoriesTitle: String? = null

    @ColumnInfo(name = "finishedOrders")
    @SerializedName("finishedOrders")
    @Expose
    var finishedOrders: String? = null

//    @ColumnInfo(name = "categories")
//    @SerializedName("categories")
//    @Expose
//    var categories: String? = null

    @ColumnInfo(name = "comments")
    @SerializedName("comments")
    @Expose
    var comments: String? = null

    @ColumnInfo(name = "revenue")
    @SerializedName("revenue")
    @Expose
    var revenue: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        if (id != other.id) return false
        if (sourceIdTitle != other.sourceIdTitle) return false
        if (phone != other.phone) return false
        if (canceledOrders != other.canceledOrders) return false
        if (deposit != other.deposit) return false
        if (debt != other.debt) return false
        if (sourceId != other.sourceId) return false
        if (smsBirthday != other.smsBirthday) return false
        if (smsExclude != other.smsExclude) return false
        if (employer != other.employer) return false
        if (job != other.job) return false
        if (lastname != other.lastname) return false
        if (discount != other.discount) return false
        if (city != other.city) return false
        if (balance != other.balance) return false
        if (lastVisit != other.lastVisit) return false
        if (address != other.address) return false
        if (smsExcludeTitle != other.smsExcludeTitle) return false
        if (smsBirthdayTitle != other.smsBirthdayTitle) return false
        if (name != other.name) return false
        if (categoriesTitle != other.categoriesTitle) return false
        if (finishedOrders != other.finishedOrders) return false
        if (comments != other.comments) return false
        if (revenue != other.revenue) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (sourceIdTitle?.hashCode() ?: 0)
        result = 31 * result + (phone?.hashCode() ?: 0)
        result = 31 * result + (canceledOrders?.hashCode() ?: 0)
        result = 31 * result + (deposit?.hashCode() ?: 0)
        result = 31 * result + (debt?.hashCode() ?: 0)
        result = 31 * result + (sourceId?.hashCode() ?: 0)
        result = 31 * result + (smsBirthday?.hashCode() ?: 0)
        result = 31 * result + (smsExclude?.hashCode() ?: 0)
        result = 31 * result + (employer?.hashCode() ?: 0)
        result = 31 * result + (job?.hashCode() ?: 0)
        result = 31 * result + (lastname?.hashCode() ?: 0)
        result = 31 * result + (discount?.hashCode() ?: 0)
        result = 31 * result + (city?.hashCode() ?: 0)
        result = 31 * result + (balance?.hashCode() ?: 0)
        result = 31 * result + (lastVisit?.hashCode() ?: 0)
        result = 31 * result + (address?.hashCode() ?: 0)
        result = 31 * result + (smsExcludeTitle?.hashCode() ?: 0)
        result = 31 * result + (smsBirthdayTitle?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (categoriesTitle?.hashCode() ?: 0)
        result = 31 * result + (finishedOrders?.hashCode() ?: 0)
        result = 31 * result + (comments?.hashCode() ?: 0)
        result = 31 * result + (revenue?.hashCode() ?: 0)
        return result
    }
}