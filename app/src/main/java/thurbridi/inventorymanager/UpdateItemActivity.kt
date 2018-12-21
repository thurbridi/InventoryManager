package thurbridi.inventorymanager

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UpdateItemActivity : AppCompatActivity() {
    private lateinit var itemsViewModel: ItemsViewModel

    private lateinit var editNameView: EditText
    private lateinit var editAmountView: EditText
    private lateinit var amountChangedView: TextView

    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_item)

        itemsViewModel = ViewModelProviders.of(this).get(ItemsViewModel::class.java)

        editNameView = findViewById(R.id.editName)
        editAmountView = findViewById(R.id.editAmount)
        amountChangedView = findViewById(R.id.amount_update)

        item = intent.getParcelableExtra<Item>("item")

        editNameView.setText(item.name)
        editAmountView.setText("${item.amount}")


        val current_amount = item.amount
        var new_amount = current_amount

        val buttonSave = findViewById<Button>(R.id.button_save)
        val buttonCancel = findViewById<Button>(R.id.button_cancel)
        val buttonAdd = findViewById<ImageButton>(R.id.button_add)
        val buttonRemove = findViewById<ImageButton>(R.id.button_remove)

        editAmountView.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                new_amount = if (TextUtils.isEmpty(editAmountView.text)) {
                    0
                } else {
                    editAmountView.text.toString().toInt()
                }

                val change = new_amount - current_amount
                if (change > 0) {
                    amountChangedView.setText("${current_amount}(+${change})")
                } else {
                    amountChangedView.setText("${current_amount}(${change})")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        })

        buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editNameView.text) or TextUtils.isEmpty(editAmountView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                this.item.name = editNameView.text.toString()
                this.item.amount = editAmountView.text.toString().toInt()
                itemsViewModel.insert(item)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        buttonCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, Intent())
            finish()
        }

        buttonAdd.setOnClickListener {
            if (TextUtils.isEmpty(editAmountView.text)) {
                editAmountView.setText("${1}")
            } else {
                val current = editAmountView.text.toString().toInt()
                editAmountView.setText("${current + 1}")
            }
        }

        buttonRemove.setOnClickListener {
            if (TextUtils.isEmpty(editAmountView.text)) {
                editAmountView.setText("${0}")
            } else {
                val current = editAmountView.text.toString().toInt()
                if (current > 0) {
                    editAmountView.setText("${current - 1}")
                }
            }
        }
    }
}