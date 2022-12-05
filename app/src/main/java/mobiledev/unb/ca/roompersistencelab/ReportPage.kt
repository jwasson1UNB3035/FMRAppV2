package mobiledev.unb.ca.roompersistencelab

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import mobiledev.unb.ca.roompersistencelab.entity.Item
import mobiledev.unb.ca.roompersistencelab.ui.ItemViewModel
import mobiledev.unb.ca.roompersistencelab.ui.ItemsAdapter
import mobiledev.unb.ca.roompersistencelab.utils.KeyboardUtils.hideKeyboard
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReportPage : AppCompatActivity() {
    private lateinit var mItemViewModel: ItemViewModel
    private lateinit var mListView: ListView
    //private lateinit var currentPhotoPath: String
    private lateinit var imageFileName: String

    private var currentPhotoPath: String? = ""
    private var mItemEditText: EditText? = null
    private var mNumberEditText: EditText? = null
    private var mResultsTextView: TextView? = null
    private var mDescriptionEditText:TextView? = null
    private var ivPhoto:ImageView? = null

    private var cameraActivityResultLauncher: ActivityResultLauncher<Intent>? = null

    val suggestion = arrayOf("Head Hall", "Harriet Irving Library", "Bailey Hall", "Student Union Building", "Currie Center")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the references for the views defined in the layout files
        mItemEditText = findViewById(R.id.item_edit_text)
<<<<<<< Updated upstream
        mNumberEditText = findViewById(R.id.number_edit_text)
        //mResultsTextView = findViewById(R.id.results_text_view)
        //mListView = findViewById(R.id.listview)
=======
        mNumberEditText = findViewById(R.id.autocompletetextview)
        mResultsTextView = findViewById(R.id.results_text_view)
        mListView = findViewById(R.id.listview)
>>>>>>> Stashed changes
        mDescriptionEditText = findViewById(R.id.des_edit_text)
        ivPhoto = findViewById(R.id.photoReport_imageview)


        val autocomp = findViewById<AutoCompleteTextView>(R.id.autocompletetextview)
        val adapterArray = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,suggestion)
        autocomp.setAdapter(adapterArray)
        autocomp.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            Toast.makeText(applicationContext,"Clicked item = "+suggestion[position],Toast.LENGTH_SHORT).show()
        })


        val mAddButton = findViewById<Button>(R.id.add_button)
        val mCameraButton = findViewById<Button>(R.id.camera_button)
        mAddButton.setOnClickListener {
            // TODO
            //  Check if some text has been entered in both the item and number EditTexts.
            //  If not display a toast indicating that the data entered was incomplete.
            //  HINT:
            //    Have a look at the TextUtils class (https://developer.android.com/reference/android/text/TextUtils)
            Log.i(TAG, "test1")
            val context = applicationContext
            val itemText = mItemEditText!!.text.toString()
            if (TextUtils.isEmpty(itemText)) {
                Toast.makeText(context,
                    getString(R.string.err_no_item_value_entered),
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val numberText = mNumberEditText!!.text.toString()
            if (TextUtils.isEmpty(numberText)) {
                Toast.makeText(context,
                    getString(R.string.err_no_number_value_entered),
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.i(TAG, "test2")
            val descriptionText = mDescriptionEditText!!.text.toString()
            if (TextUtils.isEmpty(descriptionText)) {
                Toast.makeText(context,
                        getString(R.string.err_no_des_value_entered),
                        Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.i(TAG, "test3")

            // IF PHOTO NOT MADE
            if (currentPhotoPath == "") {
                Toast.makeText(context,
                        getString(R.string.err_no_photo_entered),
                        Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val filePath = currentPhotoPath.toString()

            Log.i(TAG, filePath)

            addItem(itemText, numberText,descriptionText, filePath)

           // TODO erase the photo when you press back after report

            val intent = Intent(this@ReportPage, FeedPage::class.java)
            startActivity(intent)
        }

        mCameraButton.setOnClickListener {
            dispatchTakePhotoIntent()
        }
        setCameraActivityResultLauncher()

        /*
        mSearchEditText = findViewById(R.id.search_edit_text)
        mSearchEditText?.setOnEditorActionListener { v: TextView?, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // TODO
                //  v is the search EditText. (EditText is a subclass of TextView.)
                //  Get the text from this view.
                //  Call the searchRecords method using the item name.
                val text = v!!.text
                if (!TextUtils.isEmpty(text)) {
                    hideKeyboard(this@ReportPage)
                    //searchRecords(text.toString())
                }

                // Show error message if no search field added
                Toast.makeText(applicationContext,
                    getString(R.string.err_no_search_term_entered),
                    Toast.LENGTH_SHORT).show()
            }
            false
        } */

        // Set the ViewModel
        mItemViewModel = ViewModelProvider(this)[ItemViewModel::class.java]
    }

    private fun addItem(item: String, num: String, description: String, path: String) {
        Log.i(TAG, "test4")
        // TODO 1
        //  Make a call to the view model to create a record in the database table
        mItemViewModel.insert(item, num, description, path)

        // TODO 2
        //  You will need to write a bit of extra code to get the
        //  UI to behave nicely, e.g., showing and hiding the keyboard
        //  at the right time, clearing text fields appropriately.
        //  Some of that code will likely go here, but you might also make
        //  changes elsewhere in the app. Exactly how you make the
        //  UI behave is up to you, but you should make reasonable
        //  choices.
        //  HINT:
        //    There is a utility object called KeyboardUtils which may be helpful here

        hideKeyboard(this@ReportPage)

        // Clear the search results and fields
//        clearListView()
        updateListView(null)
        mItemEditText!!.setText("")
        mNumberEditText!!.setText("")
        mDescriptionEditText!!.setText("")
        currentPhotoPath = ""
        Log.i(TAG, "AFTER")

        Toast.makeText(applicationContext, getString(R.string.msg_record_added), Toast.LENGTH_SHORT)
            .show()



    }

    /*
    private fun displayRecords() {
        // TODO
        //  Make a call to the view model to search for records in the database that match the query item.
        //  Make sure that the results are sorted appropriately
       // val items = mItemViewModel.findItemsByName(item)
        val items = mItemViewModel.getAll()
        // TODO
        //  Update the results section.
        //  If there are no results, set the results TextView to indicate that there are no results.
        //  If there are results, set the results TextView to indicate that there are results.
        //  Again, you might need to write a bit of extra code here or elsewhere, to get the UI to behave nicely.
        //  HINT:
        //    When displaying the results you will need to set the ItemsAdapter object and the
        //    adapter attribute of mListView
        val itemsCount = items.size
        if (itemsCount <= 0) {
            mResultsTextView!!.text = getString(R.string.msg_no_results_found)
        } else {
            val text = if (itemsCount == 1) getString(R.string.msg_single_result_found,
                itemsCount) else getString(R.string.msg_multiple_results_found, itemsCount)
            mResultsTextView!!.text = text
        }

        updateListView(items)
    }


     */

//    private fun clearListView() {
//        mResultsTextView!!.text = ""
//        updateListView(ArrayList())
//    }

    private fun updateListView(items: List<Item>?) {
        Log.i(TAG, "in updateView1")
        var currItems: List<Item>? = items
        Log.i(TAG, "in updateView2")
        if (null == currItems) {
            Log.i(TAG, "in UV1")
            //mResultsTextView!!.text = ""
            Log.i(TAG, "in UV2")
            currItems = ArrayList()
            Log.i(TAG, "in UV3")
        }
        Log.i(TAG, "in updateView3")

        val mItemsAdapter = ItemsAdapter(applicationContext, currItems)
        Log.i(TAG, "in updateView4")
        //mListView.adapter = mItemsAdapter
        Log.i(TAG, "in updateView5")
        mItemsAdapter.notifyDataSetChanged()
        Log.i(TAG, "after updateView6")
    }

    // Camera methods
    private fun setCameraActivityResultLauncher() {
        cameraActivityResultLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                galleryAddPic()
            }
        }
    }

    private fun dispatchTakePhotoIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there is a camera activity to handle the intent
            try {
                // Set the File object used to save the photo
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.e(TAG, "Exception found when creating the photo save file")
                }

                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI = FileProvider.getUriForFile(
                            this,
                            "mobiledev.unb.ca.lab3intents.provider",
                            photoFile
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                    // Calling this method allows us to capture the return code
                    cameraActivityResultLauncher!!.launch(takePictureIntent)
                }
            } catch (ex: ActivityNotFoundException) {
                Log.e(TAG, "Unable to load photo activity", ex)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val TIME_STAMP_FORMAT = "yyyyMMdd_HHmmss"
        val timeStamp = SimpleDateFormat(TIME_STAMP_FORMAT, Locale.getDefault()).format(Date())
        imageFileName = "IMG_" + timeStamp + "_"
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                imageFileName,  // prefix
                ".png",  // suffix
                storageDir // directory
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }



    }

    private fun galleryAddPic() {
        Log.d(TAG, "Saving image to the gallery")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 and above
            mediaStoreAddPicToGallery()
        } else {
            // Pre Android 10
            mediaScannerAddPicToGallery()
        }
        Log.i(TAG, "Image saved!")

        var filePath = currentPhotoPath.toString()
        val bitmap = BitmapFactory.decodeFile(filePath)
        ivPhoto?.setImageBitmap(bitmap)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun mediaStoreAddPicToGallery() {
        val name = imageFileName
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath)

        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "$name.jpg")
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

        val resolver = contentResolver
        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        try {
            resolver.openOutputStream(imageUri!!).use { fos ->
                bitmap.compress(
                        Bitmap.CompressFormat.JPEG,
                        100,
                        fos
                )
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error saving the file ", e)
        }
    }

    private fun mediaScannerAddPicToGallery() {
        val file = File(currentPhotoPath)
        MediaScannerConnection.scanFile(this@ReportPage,
                arrayOf(file.toString()),
                arrayOf(file.name),
                null)
    }
}