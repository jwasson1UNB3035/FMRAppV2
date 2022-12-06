package mobiledev.unb.ca.roompersistencelab

import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import mobiledev.unb.ca.roompersistencelab.entity.Item
import mobiledev.unb.ca.roompersistencelab.ui.ItemViewModel
import mobiledev.unb.ca.roompersistencelab.ui.ItemsAdapter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FeedPage : AppCompatActivity() {

    private lateinit var mItemViewModel: ItemViewModel
    private lateinit var mListView: ListView
    private var mResultsTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feed_page)

        mResultsTextView = findViewById(R.id.results_text_view)
        mListView = findViewById(R.id.listview)

        mItemViewModel = ViewModelProvider(this)[ItemViewModel::class.java]

        // TODO ADD BACK BUTTON

        mItemViewModel.insert("light", "Head Hall", "fix light", "")

        displayRecords()


    }



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

    private fun updateListView(items: List<Item>?) {
        Log.i(ContentValues.TAG, "in updateView")
        var currItems: List<Item>? = items
        if (null == currItems) {
            mResultsTextView!!.text = ""
            currItems = ArrayList()
        }


        val mItemsAdapter = ItemsAdapter(applicationContext, currItems)
        mListView.adapter = mItemsAdapter
        mItemsAdapter.notifyDataSetChanged()
        Log.i(ContentValues.TAG, "after updateView")
    }


}