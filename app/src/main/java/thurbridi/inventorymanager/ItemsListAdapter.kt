package thurbridi.inventorymanager

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.item_card.view.*


class ItemsListAdapter internal constructor(
    context: Context,
    private val onClickRemove: (Item) -> Unit,
    private val onClickUpdate: (Item) -> Unit
    ): RecyclerView.Adapter<ItemsListAdapter.ItemViewHolder>(){


    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var items = emptyList<Item>()

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cardItemView: CardView = itemView.card_view
        val nameItemView: TextView = itemView.textView_name
        val amountItemView: TextView = itemView.textView_amount
        val moreItemView: ImageButton = itemView.button_more
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = inflater.inflate(R.layout.item_card, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]

        with(item) {
            holder.cardItemView.setOnClickListener { 
                onClickUpdate(items[position])
            }
            holder.nameItemView.text = name
            holder.amountItemView.text = amount.toString()
            holder.moreItemView.setOnClickListener {
                onClickRemove(items[position])
            }
        }

    }

    internal fun setItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}