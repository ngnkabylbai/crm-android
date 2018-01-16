package kz.mycrm.android.db.entity

import android.arch.persistence.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kz.mycrm.android.util.TimestampConverter
import java.util.*

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */
@Entity(tableName = "mOrder")
class Order : Comparable<Order> {

    @PrimaryKey
    @ColumnInfo(name="order_id")
    @SerializedName("id")
    @Expose
    lateinit var id: String

    @ColumnInfo(name="hours_before")
    @SerializedName("hours_before")
    @Expose
    var hoursBefore: String? = null

    @ColumnInfo(name="company_customer_id")
    @SerializedName("company_customer_id")
    @Expose
    var companyCustomerId: String? = null

    @ColumnInfo(name="customer_phone")
    @SerializedName("customer_phone")
    @Expose
    var customerPhone: String? = null

    @ColumnInfo(name="staff_id")
    @SerializedName("staff_id")
    @Expose
    var staffId: String? = null

    @SerializedName("customer")
    @Expose
    @Embedded
    var customer: Customer? = null

    @ColumnInfo(name="resourceId")
    @SerializedName("resourceId")
    @Expose
    var resourceId: String? = null

    @ColumnInfo(name="title")
    @SerializedName("title")
    @Expose
    var title: String? = null

    @ColumnInfo(name="company_cash_id")
    @SerializedName("company_cash_id")
    @Expose
    var companyCashId: String? = null

    @ColumnInfo(name="customer_source_id")
    @SerializedName("customer_source_id")
    @Expose
    var customerSourceId: String? = null

    @ColumnInfo(name="className")
    @SerializedName("className")
    @Expose
    var className: String? = null

    @ColumnInfo(name="contactCustomers")
    @SerializedName("contactCustomers")
    @Expose
    var contactCustomers: List<String>? = null

    @ColumnInfo(name="customer_full_name")
    @SerializedName("customer_full_name")
    @Expose
    var customerFullName: String? = null

    @ColumnInfo(name="datetime")
    @SerializedName("datetime")
    @Expose
    @TypeConverters(TimestampConverter::class)
    lateinit var datetime: Date

    @ColumnInfo(name="note")
    @SerializedName("note")
    @Expose
    var note: String? = null

    @ColumnInfo(name="end")
    @SerializedName("end")
    @Expose
    var end: String? = null

    @ColumnInfo(name="staff_fullname")
    @SerializedName("staff_fullname")
    @Expose
    var staffFullname: String? = null

    @ColumnInfo(name="files")
    @SerializedName("files")
    @Expose
    var files: List<String>? = null

    @ColumnInfo(name="services")
    @SerializedName("services")
    @Expose
    lateinit var services: List<Service>

    @ColumnInfo(name="staff_posititon")
    @SerializedName("staff_position")
    @Expose
    var staffPosition: String? = null

    @ColumnInfo(name="documents")
    @SerializedName("documents")
    @Expose
    var documents: List <String>? = null

    @ColumnInfo(name="status")
    @SerializedName("status")
    @Expose
    var status: String? = null

    @ColumnInfo(name="products_discount")
    @SerializedName("products_discount")
    @Expose
    var productsDiscount: String? = null

    @ColumnInfo(name="customer_name")
    @SerializedName("customer_name")
    @Expose
    var customerName: String? = null

//    @ColumnInfo(name="medCard")
//    @SerializedName("medCard")
//    @Expose
//    var medCard: String? = null

    @ColumnInfo(name="editable")
    @SerializedName("editable")
    @Expose
    var editable: String? = null

    @ColumnInfo(name="price")
    @SerializedName("price")
    @Expose
    var price: String? = null

    @ColumnInfo(name="color")
    @SerializedName("color")
    @Expose
    var color: String? = null

    @ColumnInfo(name="start")
    @SerializedName("start")
    @Expose
    var start: String? = null

    @ColumnInfo(name="insurance_id")
    @SerializedName("insurance_id")
    @Expose
    var insuranceId: String? = null

//    @ColumnInfo(name="")
//    @SerializedName("order_payments")
//    @Expose
//    var orderPayments: Array? = null<OrderPayment>

    @ColumnInfo(name="division_id")
    @SerializedName("division_id")
    @Expose
    var divisionId: String? = null

    @ColumnInfo(name="productsprice")
    @SerializedName("productsprice")
    @Expose
    var productsprice: String? = null

    @ColumnInfo(name="referrer_id")
    @SerializedName("referrer_id")
    @Expose
    var referrerId: String? = null

    override fun toString(): String {
        return "Order(id='$id', staffId=$staffId, customer=$customer, end=$end, start=$start)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Order

        if (id != other.id) return false
        if (hoursBefore != other.hoursBefore) return false
        if (companyCustomerId != other.companyCustomerId) return false
        if (customerPhone != other.customerPhone) return false
        if (staffId != other.staffId) return false
        if (customer != other.customer) return false
        if (resourceId != other.resourceId) return false
        if (title != other.title) return false
        if (companyCashId != other.companyCashId) return false
        if (customerSourceId != other.customerSourceId) return false
        if (className != other.className) return false
        if (customerFullName != other.customerFullName) return false
        if (datetime != other.datetime) return false
        if (note != other.note) return false
        if (end != other.end) return false
        if (staffFullname != other.staffFullname) return false
        if (services != other.services) return false
        if (staffPosition != other.staffPosition) return false
        if (status != other.status) return false
        if (productsDiscount != other.productsDiscount) return false
        if (customerName != other.customerName) return false
//        if (medCard != other.medCard) return false
        if (editable != other.editable) return false
        if (price != other.price) return false
        if (color != other.color) return false
        if (start != other.start) return false
        if (insuranceId != other.insuranceId) return false
        if (divisionId != other.divisionId) return false
        if (productsprice != other.productsprice) return false
        if (referrerId != other.referrerId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (hoursBefore?.hashCode() ?: 0)
        result = 31 * result + (companyCustomerId?.hashCode() ?: 0)
        result = 31 * result + (customerPhone?.hashCode() ?: 0)
        result = 31 * result + (staffId?.hashCode() ?: 0)
        result = 31 * result + (customer?.hashCode() ?: 0)
        result = 31 * result + (resourceId?.hashCode() ?: 0)
        result = 31 * result + (title?.hashCode() ?: 0)
        result = 31 * result + (companyCashId?.hashCode() ?: 0)
        result = 31 * result + (customerSourceId?.hashCode() ?: 0)
        result = 31 * result + (className?.hashCode() ?: 0)
        result = 31 * result + (customerFullName?.hashCode() ?: 0)
        result = 31 * result + (datetime?.hashCode() ?: 0)
        result = 31 * result + (note?.hashCode() ?: 0)
        result = 31 * result + (end?.hashCode() ?: 0)
        result = 31 * result + (staffFullname?.hashCode() ?: 0)
        result = 31 * result + (services.hashCode())
        result = 31 * result + (staffPosition?.hashCode() ?: 0)
        result = 31 * result + (status?.hashCode() ?: 0)
        result = 31 * result + (productsDiscount?.hashCode() ?: 0)
        result = 31 * result + (customerName?.hashCode() ?: 0)
//        result = 31 * result + (medCard?.hashCode() ?: 0)
        result = 31 * result + (editable?.hashCode() ?: 0)
        result = 31 * result + (price?.hashCode() ?: 0)
        result = 31 * result + (color?.hashCode() ?: 0)
        result = 31 * result + (start?.hashCode() ?: 0)
        result = 31 * result + (insuranceId?.hashCode() ?: 0)
        result = 31 * result + (divisionId?.hashCode() ?: 0)
        result = 31 * result + (productsprice?.hashCode() ?: 0)
        result = 31 * result + (referrerId?.hashCode() ?: 0)
        return result
    }

    override fun compareTo(other: Order): Int {
        return datetime.compareTo(other.datetime)
    }
}