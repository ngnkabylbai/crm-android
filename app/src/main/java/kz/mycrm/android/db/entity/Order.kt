package kz.mycrm.android.db.entity

import android.arch.persistence.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kz.mycrm.android.util.Converters

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
    lateinit var hoursBefore: String

    @ColumnInfo(name="company_customer_id")
    @SerializedName("company_customer_id")
    @Expose
    lateinit var companyCustomerId: String

    @ColumnInfo(name="customer_phone")
    @SerializedName("customer_phone")
    @Expose
    lateinit var customerPhone: String

    @ColumnInfo(name="staff_d")
    @SerializedName("staff_id")
    @Expose
    lateinit var staffId: String

    @SerializedName("customer")
    @Expose
    @Embedded
    lateinit var customer: Customer

    @ColumnInfo(name="resourceId")
    @SerializedName("resourceId")
    @Expose
    lateinit var resourceId: String

    @ColumnInfo(name="title")
    @SerializedName("title")
    @Expose
    lateinit var title: String

    @ColumnInfo(name="company_cash_id")
    @SerializedName("company_cash_id")
    @Expose
    lateinit var companyCashId: String

    @ColumnInfo(name="customer_source_id")
    @SerializedName("customer_source_id")
    @Expose
    lateinit var customerSourceId: String

    @ColumnInfo(name="className")
    @SerializedName("className")
    @Expose
    lateinit var className: String

//    @ColumnInfo(name="contactCustomers")
//    @SerializedName("contactCustomers")
//    @Expose
//    lateinit var contactCustomers: Array<String>

    @ColumnInfo(name="customer_full_name")
    @SerializedName("customer_full_name")
    @Expose
    lateinit var customerFullName: String

    @ColumnInfo(name="datetime")
    @SerializedName("datetime")
    @Expose
    lateinit var datetime: String

    @ColumnInfo(name="note")
    @SerializedName("note")
    @Expose
    lateinit var note: String

    @ColumnInfo(name="end")
    @SerializedName("end")
    @Expose
    lateinit var end: String

    @ColumnInfo(name="staff_fullname")
    @SerializedName("staff_fullname")
    @Expose
    lateinit var staffFullname: String

//    @ColumnInfo(name="files")
//    @SerializedName("files")
//    @Expose
//    lateinit var files: Array<String>

//    @ColumnInfo(name="services")
//    @SerializedName("services")
//    @Expose
//    lateinit var services: ArrayList<Service>

    @ColumnInfo(name="staff_posititon")
    @SerializedName("staff_position")
    @Expose
    lateinit var staffPosition: String

//    @ColumnInfo(name="documents")
//    @SerializedName("documents")
//    @Expose
//    lateinit var documents: Array<String>

    @ColumnInfo(name="status")
    @SerializedName("status")
    @Expose
    lateinit var status: String

    @ColumnInfo(name="products_discount")
    @SerializedName("products_discount")
    @Expose
    lateinit var productsDiscount: String

    @ColumnInfo(name="customer_name")
    @SerializedName("customer_name")
    @Expose
    lateinit var customerName: String

    @ColumnInfo(name="medCard")
    @SerializedName("medCard")
    @Expose
    lateinit var medCard: String

    @ColumnInfo(name="editable")
    @SerializedName("editable")
    @Expose
    lateinit var editable: String

    @ColumnInfo(name="price")
    @SerializedName("price")
    @Expose
    lateinit var price: String

    @ColumnInfo(name="color")
    @SerializedName("color")
    @Expose
    lateinit var color: String

    @ColumnInfo(name="start")
    @SerializedName("start")
    @Expose
    lateinit var start: String

    @ColumnInfo(name="insurance_id")
    @SerializedName("insurance_id")
    @Expose
    lateinit var insuranceId: String

//    @ColumnInfo(name="")
//    @SerializedName("order_payments")
//    @Expose
//    lateinit var orderPayments: Array<OrderPayment>

    @ColumnInfo(name="division_id")
    @SerializedName("division_id")
    @Expose
    lateinit var divisionId: String

    @ColumnInfo(name="productsprice")
    @SerializedName("productsprice")
    @Expose
    lateinit var productsprice: String

    @ColumnInfo(name="referrer_id")
    @SerializedName("referrer_id")
    @Expose
    lateinit var referrerId: String

}