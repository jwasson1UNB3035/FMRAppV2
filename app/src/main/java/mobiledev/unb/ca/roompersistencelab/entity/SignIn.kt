package mobiledev.unb.ca.roompersistencelab.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "signin_table") // Represents a SQLite table
class SignIn {
    // TODO
    //  Create the values for the entity
    //  NOTES:
    //      You will need to add variables for the column names of id, name, and num
    //      id is an autogenerated attribute
    //  Additional details can be found at https://developer.android.com/reference/android/arch/persistence/room/Entity
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var username: String? = null
    var password: String? = null
}