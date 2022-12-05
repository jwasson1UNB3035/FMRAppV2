package mobiledev.unb.ca.roompersistencelab.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mobiledev.unb.ca.roompersistencelab.entity.Item
import mobiledev.unb.ca.roompersistencelab.entity.SignIn

/**
 * This DAO object validates the SQL at compile-time and associates it with a method
 */
@Dao
interface SignInDao {
    // TODO
    //  Add app specific queries in here
    //  Additional details can be found at https://developer.android.com/reference/android/arch/persistence/room/Dao
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(signIn: SignIn?)

    @Query("SELECT * FROM signin_table WHERE username = :username and password = :password")
    fun login(username: String?, password: String?): List<SignIn>
}