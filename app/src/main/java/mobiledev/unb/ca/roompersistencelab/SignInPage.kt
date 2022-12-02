package mobiledev.unb.ca.roompersistencelab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SignInPage: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_page)

        //This code needs to be changed to go to the Report Page
        //val cameraButton = findViewById<Button>(R.id.btnSignIn)
        //cameraButton.setOnClickListener { dispatchTakePhotoIntent() }
        //setCameraActivityResultLauncher()

        val signInButton = findViewById<Button>(R.id.btnSubmit)
        signInButton.setOnClickListener {
            val intent = Intent(this@SignInPage, ReportPage::class.java)
            startActivity(intent)
        }
    }
}