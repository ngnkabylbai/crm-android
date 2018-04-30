package kz.mycrm.android.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by NKabylbay on 11/11/2017.
 */
@Entity(tableName = "token")
class Token {

    @PrimaryKey
    var id: Int = 1

    @SerializedName("token")
    @Expose
    @ColumnInfo(name = "token_data")
    lateinit var token: String
}
