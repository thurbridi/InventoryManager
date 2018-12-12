package thurbridi.inventorymanager

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
interface ItemDao {
    @Insert(onConflict = REPLACE)
    fun insert(item: Item)

    @Query("SELECT * FROM item_table ORDER BY name ASC")
    fun loadAll(): LiveData<List<Item>>

    @Query("DELETE FROM item_table")
    fun deleteAll()

    @Update
    fun update(item: Item)

    @Delete
    fun deleteItem(item: Item)
}