package thurbridi.inventorymanager

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class ItemRepository(private val itemDao: ItemDao) {
    val items: LiveData<List<Item>> = itemDao.loadAll()

    @WorkerThread
    fun insert(item: Item) = itemDao.insert(item)

    @WorkerThread
    fun delete(item: Item) = itemDao.deleteItem(item)

    @WorkerThread
    fun wipe() = itemDao.deleteAll()
}