package kz.mycrm.android.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by NKabylbay on 11/11/2017.
 */
@Entity
class Token {

    @SerializedName("token")
    @Expose
    @PrimaryKey
    @ColumnInfo(name = "token")
    lateinit var token: String
}
