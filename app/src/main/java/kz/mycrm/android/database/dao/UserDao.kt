package kz.mycrm.android.database.dao

import android.arch.persistence.room.*
import kz.mycrm.android.database.entity.User

/**
 * Created by NKabylbay on 11/11/2017.
 */
@Dao
interface UserDao {

    @Insert
    fun insertUser (user: User)

    @Update
    fun updateUser (user: User)

    @Delete
    fun deleteUser (user: User)

}