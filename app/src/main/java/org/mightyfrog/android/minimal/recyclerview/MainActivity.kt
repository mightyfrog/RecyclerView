package org.mightyfrog.android.minimal.recyclerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * RecyclerView swipe-and-dimiss, drag-and-drop sample code.
 *
 * @author Shigehiro Soejima
 */
class MainActivity : AppCompatActivity() {

    companion object {
        private val list = ArrayList<String>(100)

        init {
            for (i in 0..99) {
                list.add("#$i")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.also { rv ->
            rv.layoutManager = LinearLayoutManager(this)
            rv.adapter = MyAdapter()
            setupItemTouchHelper()
        }
    }

    //
    //
    //

    /**
     *
     */
    private fun setupItemTouchHelper() {
        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, selected: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                val from = selected.adapterPosition
                val to = target.adapterPosition
                Collections.swap(list, from, to)
                recyclerView.adapter?.notifyItemMoved(from, to)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                list.removeAt(viewHolder.adapterPosition)
                recyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
            }
        })
        helper.attachToRecyclerView(recyclerView)
    }

    //
    //
    //

    /**
     *
     */
    private class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(viewHolder: MyViewHolder, i: Int) {
            viewHolder.tv.text = list[i]
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
            val view = LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.list_item, viewGroup, false)
            view.setOnClickListener {
                // no-op, just to add click visual feedback
            }
            return MyViewHolder(view)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.text_view)
    }
}
