package kz.mycrm.android.db.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */

class Order {


    @SerializedName("id")
    @Expose
    private lateinit var id: String

    @SerializedName("hours_before")
    @Expose
    private lateinit var hoursBefore: String

    @SerializedName("company_customer_id")
    @Expose
    private lateinit var companyCustomerId: String

    @SerializedName("customer_phone")
    @Expose
    private lateinit var customerPhone: String

    @SerializedName("staff_id")
    @Expose
    private lateinit var staffId: String

    @SerializedName("customer")
    @Expose
    private lateinit var customer: Customer

    @SerializedName("resourceId")
    @Expose
    private lateinit var resourceId: String

    @SerializedName("title")
    @Expose
    private lateinit var title: String

    @SerializedName("company_cash_id")
    @Expose
    private lateinit var companyCashId: String

    @SerializedName("customer_source_id")
    @Expose
    private lateinit var customerSourceId: String

    @SerializedName("className")
    @Expose
    private lateinit var className: String

    @SerializedName("contactCustomers")
    @Expose
    private lateinit var contactCustomers: Array<String>

    @SerializedName("customer_full_name")
    @Expose
    private lateinit var customerFullName: String

    @SerializedName("datetime")
    @Expose
    private lateinit var datetime: String

    @SerializedName("note")
    @Expose
    private lateinit var note: String

    @SerializedName("end")
    @Expose
    private lateinit var end: String

    @SerializedName("staff_fullname")
    @Expose
    private lateinit var staffFullname: String

    @SerializedName("files")
    @Expose
    private lateinit var files: Array<String>

    @SerializedName("services")
    @Expose
    private lateinit var services: Array<Service>

    @SerializedName("staff_position")
    @Expose
    private lateinit var staffPosition: String

    @SerializedName("documents")
    @Expose
    private lateinit var documents: Array<String>

    @SerializedName("status")
    @Expose
    private lateinit var status: String

    @SerializedName("products_discount")
    @Expose
    private lateinit var productsDiscount: String

    @SerializedName("customer_name")
    @Expose
    private lateinit var customerName: String

    @SerializedName("medCard")
    @Expose
    private lateinit var medCard: String

    @SerializedName("editable")
    @Expose
    private lateinit var editable: String

    @SerializedName("price")
    @Expose
    private lateinit var price: String

    @SerializedName("color")
    @Expose
    private lateinit var color: String

    @SerializedName("start")
    @Expose
    private lateinit var start: String

    @SerializedName("insurance_id")
    @Expose
    private lateinit var insuranceId: String

    @SerializedName("order_payments")
    @Expose
    private lateinit var orderPayments: Array<OrderPayment>

    @SerializedName("division_id")
    @Expose
    private lateinit var divisionId: String

    @SerializedName("productsprice")
    @Expose
    private lateinit var productsprice: String

    @SerializedName("referrer_id")
    @Expose
    private lateinit var referrerId: String

}