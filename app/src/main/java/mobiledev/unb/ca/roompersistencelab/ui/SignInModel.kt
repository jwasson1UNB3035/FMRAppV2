package mobiledev.unb.ca.roompersistencelab.ui

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import mobiledev.unb.ca.roompersistencelab.entity.SignIn
import mobiledev.unb.ca.roompersistencelab.repository.ItemRepository
import mobiledev.unb.ca.roompersistencelab.repository.SignInRepository

class SignInModel(application: Application) : AndroidViewModel(application){

    private val signInRepository: SignInRepository = SignInRepository(application)

    fun insert(username: String?, password: String?) {
        signInRepository.insertRecord(username, password)
        Log.i(ContentValues.TAG, "in view model")
    }

    fun login(username: String?, password: String?): List<SignIn> {
        return signInRepository.login(username, password)
    }

}