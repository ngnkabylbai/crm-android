package kz.mycrm.android.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Nurbek Kabylbay on 06.02.2018.
 */
@Entity
class AppVersion {

    @PrimaryKey
    lateinit var name: String

    lateinit var version: String
}