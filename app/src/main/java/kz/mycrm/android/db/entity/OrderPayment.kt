package kz.mycrm.android.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Nurbek Kabylbay on 04.12.2017.
 */

@Entity(tableName = "orderpayment")
class OrderPayment {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "orderPayment_id")
    var id = 0

    @SerializedName("order_id")
    @Expose
    @ColumnInfo(name = "order_id")
    lateinit var orderId: String

    @SerializedName("value")
    @Expose
    @ColumnInfo(name = "value")
    lateinit var value: String

    @SerializedName("payment_id")
    @Expose
    @ColumnInfo(name = "payment_id")
    lateinit var paymentId: String
}