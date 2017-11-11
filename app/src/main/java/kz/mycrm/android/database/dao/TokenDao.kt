package kz.mycrm.android.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import kz.mycrm.android.database.entity.Token

/**
 * Created by NKabylbay on 11/11/2017.
 */
@Dao
interface TokenDao {
    @Insert
    fun insertToken(token: Token)

    @Update
    fun updateToken(token: Token)

    @Delete
    fun deleteToken(token: Token)

    @Query("SELECT * FROM token limit 1")
    fun getToken(): LiveData<Token>
}