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
   var servicePrice: String? = null

   @ColumnInfo(name = "duration")
   @SerializedName("duration")
   @Expose
   var duration: String? = null

   @ColumnInfo(name = "price")
   @SerializedName("price")
   @Expose
   var price: String? = null

   @ColumnInfo(name = "order_service_id")
   @SerializedName("order_service_id")
   @Expose
   var orderServiceId: String? = null

   @ColumnInfo(name = "name")
   @SerializedName("name")
   @Expose
   var name: String? = null

   @ColumnInfo(name = "quantity")
   @SerializedName("quantity")
   @Expose
   var quantity: String? = null

   @ColumnInfo(name = "service_name")
   @SerializedName("service_name")
   @Expose
   var serviceName: String? = null

   @ColumnInfo(name = "discount")
   @SerializedName("discount")
   @Expose
   var discount: String? = null

   override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as Service

      if (id != other.id) return false
      if (servicePrice != other.servicePrice) return false
      if (duration != other.duration) return false
      if (price != other.price) return false
      if (orderServiceId != other.orderServiceId) return false
      if (name != other.name) return false
      if (quantity != other.quantity) return false
      if (serviceName != other.serviceName) return false
      if (discount != other.discount) return false

      return true
   }

   override fun hashCode(): Int {
      var result = id.hashCode()
      result = 31 * result + (servicePrice?.hashCode() ?: 0)
      result = 31 * result + (duration?.hashCode() ?: 0)
      result = 31 * result + (price?.hashCode() ?: 0)
      result = 31 * result + (orderServiceId?.hashCode() ?: 0)
      result = 31 * result + (name?.hashCode() ?: 0)
      result = 31 * result + (quantity?.hashCode() ?: 0)
      result = 31 * result + (serviceName?.hashCode() ?: 0)
      result = 31 * result + (discount?.hashCode() ?: 0)
      return result
   }

//   @ColumnInfo(name = "products")
//   @SerializedName("products")
//   @Expose
//   var products: Array<String>? = null



}