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

@Entity
class Division {

    @PrimaryKey
    @ColumnInfo(name="id")
    @SerializedName("id")
    @Expose
    lateinit var id: String

    @ColumnInfo(name="company_id")
    @SerializedName("company_id")
    @Expose
    var companyId: String? = null

    @ColumnInfo(name="working_start")
    @SerializedName("working_start")
    @Expose
    var workingStart: String? = null

    @ColumnInfo(name="city_name")
    @SerializedName("city_name")
    @Expose
    var cityName: String? = null

    @ColumnInfo(name="phone")
    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @ColumnInfo(name="status")
    @SerializedName("status")
    @Expose
    var status: String? = null

    @ColumnInfo(name="category_id")
    @SerializedName("category_id")
    @Expose
    var categoryId: String? = null

    @ColumnInfo(name="url")
    @SerializedName("url")
    @Expose
    var url: String? = null

    @ColumnInfo(name="city_id")
    @SerializedName("city_id")
    @Expose
    var cityId: String? = null

    @ColumnInfo(name="address")
    @SerializedName("address")
    @Expose
    var address: String? = null

    @ColumnInfo(name="description")
    @SerializedName("description")
    @Expose
    var description: String? = null

    @ColumnInfo(name="name")
    @SerializedName("name")
    @Expose
    var name: String? = null

    @ColumnInfo(name="longitude")
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null

    @ColumnInfo(name="rating")
    @SerializedName("rating")
    @Expose
    var rating: String? = null

    @ColumnInfo(name="latitude")
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null

    @ColumnInfo(name="working_finish")
    @SerializedName("working_finish")
    @Expose
    var workingFinish: String? = null

    @ColumnInfo(name="key")
    @SerializedName("key")
    @Expose
    var key: String? = null

    @SerializedName("self-staff")
    @Expose
    var user: User? = null
    override fun toString(): String {
        return "Division(id=$id, companyId=$companyId, workingStart=$workingStart, cityName=$cityName, phone=$phone, status=$status, categoryId=$categoryId, url=$url, cityId=$cityId, address=$address, description=$description, name=$name, longitude=$longitude, rating=$rating, latitude=$latitude, workingFinish=$workingFinish, key=$key)"
    }
}