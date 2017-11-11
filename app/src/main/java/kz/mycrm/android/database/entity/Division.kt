package kz.mycrm.android.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * Created by NKabylbay on 11/11/2017.
 */

@Entity(foreignKeys = arrayOf(ForeignKey(onDelete = ForeignKey.CASCADE,
        entity = User::class, parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("user_id"))))
class Division {

    @PrimaryKey
    @ColumnInfo(name="id")
    val id: String? = null

    @ColumnInfo(name = "user_id")
    var userId: Int = 0

    @ColumnInfo(name="company_id")
    val companyId: String? = null

    @ColumnInfo(name="working_start")
    val workingStart: String? = null

    @ColumnInfo(name="city_name")
    val cityName: String? = null

    @ColumnInfo(name="phone")
    val phone: String? = null

    @ColumnInfo(name="status")
    val status: String? = null

    @ColumnInfo(name="category_id")
    val categoryId: String? = null

    @ColumnInfo(name="url")
    val url: String? = null

    @ColumnInfo(name="city_id")
    val cityId: String? = null

    @ColumnInfo(name="address")
    val address: String? = null

    @ColumnInfo(name="description")
    val description: String? = null

    @ColumnInfo(name="name")
    val name: String? = null

    @ColumnInfo(name="longitude")
    val longitude: String? = null

    @ColumnInfo(name="rating")
    val rating: String? = null

    @ColumnInfo(name="latitude")
    val latitude: String? = null

    @ColumnInfo(name="working_finish")
    val workingFinish: String? = null

    @ColumnInfo(name="key")
    val key: String? = null
}