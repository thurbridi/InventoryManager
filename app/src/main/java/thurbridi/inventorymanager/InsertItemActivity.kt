package thurbridi.inventorymanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class InsertItemActivity: AppCompatActivity() {
    private lateinit var editNameView: EditText
    private lateinit var editAmountView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_item)

        editNameView = findViewById(R.id.editName)
        editAmountView = findViewById(R.id.editAmount)

        val buttonSave = findViewById<Button>(R.id.button_save)
        val buttonCancel = findViewById<Button>(R.id.button_cancel)

        buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editNameView.text) or TextUtils.isEmpty(editAmountView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = editNameView.text.toString()
                val amount = editAmountView.text.toString()
                replyIntent.putExtra(EXTRA_NAME, name)
                replyIntent.putExtra(EXTRA_AMOUNT, amount)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }

        buttonCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, Intent())
            finish()
        }
    }

    companion object {
        const val EXTRA_NAME = "com.example.android.item.EXTRA_NAME"
        const val EXTRA_AMOUNT = "com.example.android.item.EXTRA_AMOUNT"
    }
}