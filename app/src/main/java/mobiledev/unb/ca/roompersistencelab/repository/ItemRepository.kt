package mobiledev.unb.ca.roompersistencelab.repository

import mobiledev.unb.ca.roompersistencelab.db.AppDatabase.Companion.getDatabase
import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import mobiledev.unb.ca.roompersistencelab.dao.ItemDao
import mobiledev.unb.ca.roompersistencelab.db.AppDatabase
import mobiledev.unb.ca.roompersistencelab.entity.Item
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class ItemRepository(application: Application) {
    private val itemDao: ItemDao? = getDatabase(application).itemDao()


    // TODO Add query specific methods
    //  HINT 1:
    //   The insert operation will use the Runnable interface as there are no return values
    //  HINT 2:
    //   The search operation will use the Callable interface with Future as there are return values
    //  HINT 3:
    //    LiveData does not work as well for this; consider using an object list to return the search results
    //  See the example project file at
    //  https://github.com/hpowell20/cs2063-fall-2022-examples/blob/master/Lecture7/RoomPersistenceLibraryDemo/app/src/main/java/mobiledev/unb/ca/roompersistencetest/repository/ItemRepository.java
    //  to see examples of how to work with the Executor Service along with Runnables and Callables

    fun insertRecord(name: String?, num: String?, des: String?) {
        Log.i(TAG, "in ItemRepos - insertRec1")
        val newItem = Item()
        newItem.name = name
        newItem.num = num
        newItem.description = des
        insertRecord(newItem)
    }

    private fun insertRecord(item: Item) {
        Log.i(TAG, "in ItemRepos - insertRec2")
        AppDatabase.databaseWriterExecutor.execute { itemDao!!.insert(item) }
        Log.i(TAG, "in ItemRepos - insertRec3")
    }

    /*fun findItemsWithName(name: String?): List<Item> {
        val dataReadFuture: Future<List<Item>> = AppDatabase.databaseWriterExecutor.submit(
            Callable {
                itemDao!!.findItemsWithName(name)
            })
        return try {
            while (!dataReadFuture.isDone) {
                // Simulating another task
                TimeUnit.SECONDS.sleep(1)
            }
            dataReadFuture.get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
            emptyList()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            emptyList()
        }
    }
    */


    fun getAll(): List<Item> {
        val dataReadFuture: Future<List<Item>> = AppDatabase.databaseWriterExecutor.submit(
                Callable {
                    itemDao!!.getAll()
                })
        return try {
            while (!dataReadFuture.isDone) {
                // Simulating another task
                TimeUnit.SECONDS.sleep(1)
            }
            dataReadFuture.get()
        } catch (e: ExecutionException) {
            e.printStackTrace()
            emptyList()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            emptyList()
        }
    }
}