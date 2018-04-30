package kz.mycrm.android.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */

@Entity
class Service() : Serializable {

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
   @SerializedName("average_time")
   @Expose
   var duration: Int = 0

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
   var quantity: Int = 1

   @ColumnInfo(name = "service_name")
   @SerializedName("service_name")
   @Expose
   var serviceName: String? = null

   @ColumnInfo(name = "discount")
   @SerializedName("discount")
   @Expose
   var discount: Int = 0

   constructor(id: String, serviceName: String): this() {
      this.id = id
      this.serviceName = serviceName
   }

   constructor(id: String, serviceName: String, price: String): this() {
      this.id = id
      this.serviceName = serviceName
      this.price = price
   }

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
      result = 31 * result + duration
      result = 31 * result + (price?.hashCode() ?: 0)
      result = 31 * result + (orderServiceId?.hashCode() ?: 0)
      result = 31 * result + (name?.hashCode() ?: 0)
      result = 31 * result + quantity
      result = 31 * result + (serviceName?.hashCode() ?: 0)
      result = 31 * result + discount
      return result
   }

   override fun toString(): String {
      return "Service(id='$id', servicePrice=$servicePrice, duration=$duration, price=$price, orderServiceId=$orderServiceId, name=$name, quantity=$quantity, serviceName=$serviceName, discount=$discount)"
   }
//   @ColumnInfo(name = "products")
//   @SerializedName("products")
//   @Expose
//   var products: Array<String>? = null
}