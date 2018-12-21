package thurbridi.inventorymanager

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var itemsViewModel: ItemsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ItemsListAdapter(this, {itemsViewModel.delete(it)}, {
            val intent = Intent(this@MainActivity, UpdateItemActivity::class.java).apply {
                putExtra("item", it)
            }
            startActivityForResult(intent, updateItemActivityRequestCode)
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        itemsViewModel = ViewModelProviders.of(this).get(ItemsViewModel::class.java)
        itemsViewModel.items.observe(this, Observer { items ->
            items?.let { adapter.setItems(it) }
        })

        fab.setOnClickListener { view ->
            val intent = Intent(this@MainActivity, UpdateItemActivity::class.java).apply {
                putExtra("item", Item(0, "", 0))
            }
            startActivityForResult(intent, insertItemActivityRequestCode)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            insertItemActivityRequestCode -> {
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(applicationContext, R.string.saved, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
                }
            }

            updateItemActivityRequestCode -> {
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(applicationContext, R.string.updated, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext, R.string.not_updated, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        const val insertItemActivityRequestCode = 1
        const val updateItemActivityRequestCode = 2
    }
}
