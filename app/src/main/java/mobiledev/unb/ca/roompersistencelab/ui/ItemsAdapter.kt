package mobiledev.unb.ca.roompersistencelab.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ArrayAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import mobiledev.unb.ca.roompersistencelab.R
import android.widget.TextView
import mobiledev.unb.ca.roompersistencelab.entity.Item

class ItemsAdapter(context: Context, items: List<Item>) : ArrayAdapter<Item>(
    context, 0, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the data item for this position
        Log.i(TAG, "in start items adapter")
        val item = getItem(position)

        // Check if an existing view is being reused, otherwise inflate the view
        var currView = convertView
        if (currView == null) {
            currView = LayoutInflater.from(context).inflate(R.layout.list_layout, parent, false)
        }

        // Lookup view for data population
        val tvName = currView!!.findViewById<TextView>(R.id.item_textview)
        val tvNum = currView.findViewById<TextView>(R.id.num_textview)
        val tvDescription = currView.findViewById<TextView>(R.id.des_textview)
        val ivPhoto: ImageView = currView.findViewById<ImageView>(R.id.photo_imageview)

        // TODO
        //  Set the text used by tvName and tvNum using the data object
        //  This will need to updated once the entity model has been updated
        tvName.text = item!!.name
        tvNum.text = item!!.num
        tvDescription.text = item!!.description

        var filePath = item!!.filePath
        val bitmap = BitmapFactory.decodeFile(filePath)
        ivPhoto.setImageBitmap(bitmap)


        Log.i(TAG, "in items adapter")

        // Return the completed view to render on screen
        return currView
    }


}