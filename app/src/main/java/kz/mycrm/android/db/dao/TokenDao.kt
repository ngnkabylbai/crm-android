package kz.mycrm.android.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import kz.mycrm.android.db.entity.Token

/**
 * Created by NKabylbay on 11/11/2017.
 */
@Dao
interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToken(token: Token)

    @Update
    fun updateToken(token: Token)

    @Delete
    fun deleteToken(token: Token)
// TODO: Bug with no data
    @Query("SELECT token_data FROM token limit 1")
    fun getToken(): LiveData<Token>
}