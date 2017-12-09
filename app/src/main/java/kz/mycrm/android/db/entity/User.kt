package kz.mycrm.android.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by NKabylbay on 11/11/2017.
 */
@Entity
class User {

    @SerializedName("id")
    @Expose
    @ColumnInfo
    @PrimaryKey
    lateinit var id: String

    @SerializedName("position_id")
    @Expose
    @ColumnInfo
    lateinit var positionId: String

    @SerializedName("name")
    @Expose
    @ColumnInfo
    lateinit var name: String

//    @SerializedName("_links")
//    @Expose
//    @ColumnInfo
//    lateinit var links: ArrayList<String>

    @SerializedName("image")
    @Expose
    @ColumnInfo
    lateinit var image: String

    @SerializedName("surname")
    @Expose
    @ColumnInfo
    lateinit var surname: String

    @SerializedName("rating")
    @Expose
    @ColumnInfo
    lateinit var rating: String
}