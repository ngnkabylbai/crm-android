package kz.mycrm.android.db.entity

import android.arch.persistence.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */
//@Entity(tableName = "mOrder", indices = arrayOf(Index(value = "services", unique = true)))
@Entity(tableName = "mOrder")
class Order {

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

//    @ColumnInfo(name="contactCustomers")
//    @SerializedName("contactCustomers")
//    @Expose
//    var contactCustomers: Array? = null<String>

    @ColumnInfo(name="customer_full_name")
    @SerializedName("customer_full_name")
    @Expose
    var customerFullName: String? = null

    @ColumnInfo(name="datetime")
    @SerializedName("datetime")
    @Expose
    var datetime: String? = null

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

//    @ColumnInfo(name="files")
//    @SerializedName("files")
//    @Expose
//    var files: Array? = null<String>

    @ColumnInfo(name="services")
    @SerializedName("services")
    @Expose
    var services: ArrayList<Service>? = null

    @ColumnInfo(name="staff_posititon")
    @SerializedName("staff_position")
    @Expose
    var staffPosition: String? = null

//    @ColumnInfo(name="documents")
//    @SerializedName("documents")
//    @Expose
//    var documents: Array? = null<String>

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

    @ColumnInfo(name="medCard")
    @SerializedName("medCard")
    @Expose
    var medCard: String? = null

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

}