package mobiledev.unb.ca.roompersistencelab

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import mobiledev.unb.ca.roompersistencelab.entity.SignIn
import mobiledev.unb.ca.roompersistencelab.ui.ItemViewModel
import mobiledev.unb.ca.roompersistencelab.ui.SignInModel
import mobiledev.unb.ca.roompersistencelab.utils.KeyboardUtils

class SignInPage: AppCompatActivity() {

    private lateinit var mSignInModel: SignInModel

    private var usernameEditText: EditText? = null
    private var passwordEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_page)

        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)


        //This code needs to be changed to go to the Report Page
        //val cameraButton = findViewById<Button>(R.id.btnSignIn)
        //cameraButton.setOnClickListener { dispatchTakePhotoIntent() }
        //setCameraActivityResultLauncher()

        val signInButton = findViewById<Button>(R.id.btnSubmit)

        signInButton.setOnClickListener {

            val context = applicationContext
            val usernameText = usernameEditText!!.text.toString()
            val passwordText = passwordEditText!!.text.toString()

            if (TextUtils.isEmpty(usernameText) || TextUtils.isEmpty(passwordText)) {
                Toast.makeText(
                    context,
                    "Please Enter Both an Email and a Password",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else{
                //write SQL for checkuserpass
                val verify = checkuserpass(usernameText, passwordText)
                if(verify){
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignInPage, ReportPage::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(context, "Incorrect Email or Password", Toast.LENGTH_SHORT).show()
                }
            }

        }
        mSignInModel = ViewModelProvider(this)[SignInModel::class.java]
        //hard coding in values for login to database
        addItem("jsmith1@unb.ca", "password")
    }

    private fun addItem(username: String, password: String){
        mSignInModel.insert(username, password)
    }

    private fun checkuserpass(username: String, password: String): Boolean {
        val result = mSignInModel.login(username, password)

        val num = result.size
        if (num <= 0){
            return false
        }
        else{
            return true
        }
    }
}