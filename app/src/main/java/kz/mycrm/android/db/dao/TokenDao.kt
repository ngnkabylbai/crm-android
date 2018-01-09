package kz.mycrm.android.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import kz.mycrm.android.db.entity.Token

/**
 * Created by NKabylbay on 11/11/2017.
 */
@Dao
interface TokenDao {
    @Insert
    fun insertToken(token: Token)

    @Update
    fun updateToken(token: Token)

    @Query("SELECT * FROM token LIMIT 1")
    fun getTokenLiveData(): LiveData<Token>

    @Query("SELECT * FROM token LIMIT 1")
    fun getToken(): Token

    @Query("SELECT COUNT(*) FROM token")
    fun getCount() : Int

    @Query("DELETE FROM token")
    fun nukeToken()
}