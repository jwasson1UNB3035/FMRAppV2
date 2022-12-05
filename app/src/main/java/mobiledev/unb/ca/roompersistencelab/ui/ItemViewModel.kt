package mobiledev.unb.ca.roompersistencelab.ui

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import mobiledev.unb.ca.roompersistencelab.entity.Item
import mobiledev.unb.ca.roompersistencelab.repository.ItemRepository

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    private val itemRepository: ItemRepository = ItemRepository(application)
//    val searchResults: List<Item>

    // TODO
    //  Add mapping calls between the UI and Database
    fun insert(name: String?, num: String?, des: String?, path: String?) {
        itemRepository.insertRecord(name, num, des, path)
        Log.i(TAG, "in view model")
    }

    /*
    fun findItemsByName(name: String?): List<Item> {
        return itemRepository.findItemsWithName(name)
    }

     */


    fun getAll(): List<Item> {
        Log.i(TAG, "in display reports view")
        return itemRepository.getAll()

    }
}