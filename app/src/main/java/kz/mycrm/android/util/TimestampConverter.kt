package kz.mycrm.android.util

import android.arch.persistence.room.TypeConverter

import java.text.ParseException
import java.util.Date

import kz.mycrm.android.db.entity.Order

/**
 * Created by Nurbek Kabylbay on 16.01.2018.
 */

class TimestampConverter {

    @TypeConverter
    fun convertStringToDate(value: String): Date {
            try {
                return Constants.orderDateTimeFormat.parse(value)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        return Date()
    }

    @TypeConverter
    fun convertDateToString(value: Date): String {
        try {
            return Constants.orderDateTimeFormat.format(value)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return Constants.orderDateTimeFormat.format(Date())
    }
}
