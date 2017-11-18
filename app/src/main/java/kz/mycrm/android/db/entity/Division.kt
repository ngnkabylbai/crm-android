package kz.mycrm.android.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by NKabylbay on 11/11/2017.
 */

@Entity(foreignKeys = arrayOf(ForeignKey(onDelete = ForeignKey.CASCADE,
        entity = User::class, parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("user_id"))))
class Division {

    @PrimaryKey
    @ColumnInfo(name="id")
    @SerializedName("id")
    @Expose
    val id: String? = null

    @ColumnInfo(name="company_id")
    @SerializedName("company_id")
    @Expose
    val companyId: String? = null

    @ColumnInfo(name="working_start")
    @SerializedName("working_start")
    @Expose
    val workingStart: String? = null

    @ColumnInfo(name="city_name")
    @SerializedName("city_name")
    @Expose
    val cityName: String? = null

    @ColumnInfo(name="phone")
    @SerializedName("phone")
    @Expose
    val phone: String? = null

    @ColumnInfo(name="status")
    @SerializedName("status")
    @Expose
    val status: String? = null

    @ColumnInfo(name="category_id")
    @SerializedName("category_id")
    @Expose
    val categoryId: String? = null

    @ColumnInfo(name="url")
    @SerializedName("url")
    @Expose
    val url: String? = null

    @ColumnInfo(name="city_id")
    @SerializedName("city_id")
    @Expose
    val cityId: String? = null

    @ColumnInfo(name="address")
    @SerializedName("address")
    @Expose
    val address: String? = null

    @ColumnInfo(name="description")
    @SerializedName("description")
    @Expose
    val description: String? = null

    @ColumnInfo(name="name")
    @SerializedName("name")
    @Expose
    val name: String? = null

    @ColumnInfo(name="longitude")
    @SerializedName("longitude")
    @Expose
    val longitude: String? = null

    @ColumnInfo(name="rating")
    @SerializedName("rating")
    @Expose
    val rating: String? = null

    @ColumnInfo(name="latitude")
    @SerializedName("latitude")
    @Expose
    val latitude: String? = null

    @ColumnInfo(name="working_finish")
    @SerializedName("working_finish")
    @Expose
    val workingFinish: String? = null

    @ColumnInfo(name="key")
    @SerializedName("key")
    @Expose
    val key: String? = null

    @ColumnInfo(name = "user_id")
    var userId: Int = 0

    override fun toString(): String {
        return "Division(id=$id, companyId=$companyId, workingStart=$workingStart, cityName=$cityName, phone=$phone, status=$status, categoryId=$categoryId, url=$url, cityId=$cityId, address=$address, description=$description, name=$name, longitude=$longitude, rating=$rating, latitude=$latitude, workingFinish=$workingFinish, key=$key, userId=$userId)"
    }


}