package kz.mycrm.android.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */

@Entity
class Service {

   @PrimaryKey
   @ColumnInfo(name = "service_id")
   @SerializedName("id")
   @Expose
   lateinit var id: String

   @ColumnInfo(name = "service_price")
   @SerializedName("service_price")
   @Expose
   lateinit var servicePrice: String

   @ColumnInfo(name = "duration")
   @SerializedName("duration")
   @Expose
   lateinit var duration: String

   @ColumnInfo(name = "price")
   @SerializedName("price")
   @Expose
   lateinit var price: String

   @ColumnInfo(name = "order_service_id")
   @SerializedName("order_service_id")
   @Expose
   lateinit var orderServiceId: String

   @ColumnInfo(name = "name")
   @SerializedName("name")
   @Expose
   lateinit var name: String

   @ColumnInfo(name = "quantity")
   @SerializedName("quantity")
   @Expose
   lateinit var quantity: String

   @ColumnInfo(name = "service_name")
   @SerializedName("service_name")
   @Expose
   var serviceName: String? = null

   @ColumnInfo(name = "discount")
   @SerializedName("discount")
   @Expose
   lateinit var discount: String

//   @ColumnInfo(name = "products")
//   @SerializedName("products")
//   @Expose
//   var products: Array<String>? = null
}