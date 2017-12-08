package kz.mycrm.android.util

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import kz.mycrm.android.db.entity.Service


/**
 * Created by asset on 12/8/17.
 */
object Converters {

    var strSeparator = "__,__"

    @TypeConverter
    fun convertListToString(video: ArrayList<Service>): String {
        val videoArray = arrayOfNulls<Service>(video.size)
        for (i in 0 until video.size) {
            videoArray[i] = video[i]
        }
        var str = ""
        val gson = Gson()
        for (i in videoArray.indices) {
            val jsonString = gson.toJson(videoArray[i])
            str += jsonString
            if (i < videoArray.size - 1) {
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