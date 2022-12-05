package mobiledev.unb.ca.roompersistencelab.repository

import mobiledev.unb.ca.roompersistencelab.db.AppDatabase.Companion.getDatabase
import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import mobiledev.unb.ca.roompersistencelab.dao.ItemDao
import mobiledev.unb.ca.roompersistencelab.dao.SignInDao
import mobiledev.unb.ca.roompersistencelab.db.AppDatabase
import mobiledev.unb.ca.roompersistencelab.entity.Item
import mobiledev.unb.ca.roompersistencelab.entity.SignIn
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class SignInRepository(application: Application) {
    private val signInDao: SignInDao? = getDatabase(application).signInDao()

    fun insertRecord(username: String?, password: String?) {
        Log.i(TAG, "in SignInRepos - insertRec1")
        val newSignIn = SignIn()
        newSignIn.username = username
        newSignIn.password = password
        insertRecord(newSignIn)
    }

    private fun insertRecord(signIn: SignIn) {
        Log.i(TAG, "in SignInRepos - insertRec2")
        AppDatabase.databaseWriterExecutor.execute { signInDao!!.insert(signIn) }
        Log.i(TAG, "in SignInRepos - insertRec3")
    }

    fun login(username: String?, password: String?): List<SignIn>{
        val dataReadFuture: Future<List<SignIn>> = AppDatabase.databaseWriterExecutor.submit(
            Callable {
                signInDao!!.login(username, password)
            })
        return try{
            while(!dataReadFuture.isDone){
                TimeUnit.SECONDS.sleep(1)
            }
            dataReadFuture.get()
        }catch (e: ExecutionException) {
            e.printStackTrace()
            emptyList()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            emptyList()
        }

    }

}