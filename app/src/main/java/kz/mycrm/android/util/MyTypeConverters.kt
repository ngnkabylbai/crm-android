package kz.mycrm.android.util

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import kz.mycrm.android.db.entity.Service


/**
 * Created by asset on 12/8/17.
 */
class MyTypeConverters {

    var strSeparator = "__,__"

    @TypeConverter
    fun convertListToString(services: ArrayList<Service>): String {
        val serviceArray = arrayOfNulls<Service>(services.size)
        for (i in 0 until services.size) {
            serviceArray[i] = services[i]
        }
        var str = ""
        val gson = Gson()
        for (i in serviceArray.indices) {
            val jsonString = gson.toJson(serviceArray[i])
            str += jsonString
            if (i < serviceArray.size - 1) {
                str += strSeparator
            }
        }
        return str
    }

    @TypeConverter
    fun convertStringToList(serviceString: String): ArrayList<Service> {
        val videoArray = serviceString.split(strSeparator.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val videos = ArrayList<Service>()
        val gson = Gson()
        for (i in 0 until videoArray.size - 1) {
            videos.add(gson.fromJson<Service>(videoArray[i], Service::class.java))
        }
        return videos
    }
}