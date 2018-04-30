package kz.mycrm.android.db.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Nurbek Kabylbay on 08.02.2018.
 */
@Entity
class DummyString {

    @PrimaryKey
    lateinit var dummyValue: String

}