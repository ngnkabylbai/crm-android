package kz.mycrm.android.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by NKabylbay on 11/11/2017.
 */
@Entity
class Token {
    @PrimaryKey
    @ColumnInfo(name = "token") lateinit var token: String
}