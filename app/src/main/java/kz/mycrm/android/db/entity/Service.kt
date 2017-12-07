package kz.mycrm.android.db.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */
class Service {

   @SerializedName("id")
   @Expose
   private lateinit var id: String

   @SerializedName("service_price")
   @Expose
   private lateinit var servicePrice: String

   @SerializedName("duration")
   @Expose
   private lateinit var duration: String

   @SerializedName("price")
   @Expose
   private lateinit var price: String

   @SerializedName("order_service_id")
   @Expose
   private lateinit var orderServiceId: String

   @SerializedName("name")
   @Expose
   private lateinit var name: String

   @SerializedName("quantity")
   @Expose
   private lateinit var quantity: String

   @SerializedName("service_name")
   @Expose
   private lateinit var serviceName: String

   @SerializedName("discount")
   @Expose
   private lateinit var discount: String

   @SerializedName("products")
   @Expose
   private lateinit var products: Array<String>
}