package kz.mycrm.android.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by NKabylbay on 11/11/2017.
 */
@Entity
class User {

    @PrimaryKey()
    @ColumnInfo(name = "user_id")
    lateinit var userNumber: String

    @ColumnInfo(name = "user_name")
    lateinit var userName: String

//    var company

}