package com.example.appjanelapopup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val list: MutableList<String>): RecyclerView.Adapter<MyAdapter.MyHolder>() {
    var onItemRecyclerView: OnItemRecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyHolder, position: Int) {
        val nome = this.list[position]
        holder.tvNome.text = nome
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    fun add(name: String) {
        this.list.add(name)
        this.notifyItemInserted(this.list.size)
    }

    fun del(position: Int) {
        this.list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.list.size)
    }

    fun mov(firstNamePosition: Int, secondNamePosition: Int) {
        val tmp = this.list[firstNamePosition]
        this.list[firstNamePosition] = this.list[secondNamePosition]
        this.list[secondNamePosition] = tmp
        this.notifyItemChanged(firstNamePosition)
        this.notifyItemChanged(secondNamePosition)
    }

    inner class MyHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        var tvNome: TextView

        init {
            this.tvNome = itemView.findViewById(R.id.tvNome)

            itemView.setOnClickListener {
                this@MyAdapter.onItemRecyclerView?.onItemClick(this.adapterPosition)
            }
        }
    }
}