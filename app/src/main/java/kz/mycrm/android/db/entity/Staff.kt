package kz.mycrm.android.db.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by NKabylbay on 11/11/2017.
 */
class Staff {

    @SerializedName("id")
    @Expose
    lateinit var id: String

    @SerializedName("position_id")
    @Expose
    lateinit var positionId: String

    @SerializedName("name")
    @Expose
    lateinit var name: String

//    @SerializedName("_links")
//    @Expose
//    @ColumnInfoname = ("
//    lateinit var links: ArrayList<String>

    @SerializedName("image")
    @Expose
    lateinit var image: String

    @SerializedName("surname")
    @Expose
    lateinit var surname: String

    @SerializedName("rating")
    @Expose
    lateinit var rating: String

    override fun toString(): String {
        return "User(id='$id', positionId='$positionId', name='$name', image='$image', surname='$surname', rating='$rating')"
    }
}