package thurbridi.inventorymanager

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var itemViewModel: ItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ItemListAdapter(this) {itemViewModel.delete(it)}
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel::class.java)

        itemViewModel.items.observe(this, Observer { items ->
            items?.let { adapter.setItems(it) }
        })

        fab.setOnClickListener { view ->
            val intent = Intent(this@MainActivity, InsertItemActivity::class.java)
            startActivityForResult(intent, insertItemActivityRequestCode)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == insertItemActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let {
                val item = Item(
                    0,
                    it.getStringExtra(InsertItemActivity.EXTRA_NAME),
                    it.getStringExtra(InsertItemActivity.EXTRA_AMOUNT).toInt()
                )
                itemViewModel.insert(item)
            }
        } else {
            Toast.makeText(applicationContext, R.string.empty_not_saved, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val insertItemActivityRequestCode = 1
    }
}
