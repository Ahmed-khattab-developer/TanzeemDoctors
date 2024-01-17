package com.tanzeem.tanzeemDoctors.ui.adapter

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.tanzeem.tanzeemDoctors.databinding.MyTextViewBinding

class MyAdapter(private val context: Context, private val myDataset: List<String>) :
    RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder>() {

    private lateinit var dataBinding: MyTextViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterViewHolder {
        dataBinding = MyTextViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyAdapterViewHolder(context, dataBinding)
    }

    override fun onBindViewHolder(holder: MyAdapterViewHolder, position: Int) {
        val myDataset = myDataset[position]
        holder.bind(myDataset)
        holder.itemView.setOnClickListener {
            setClipboard(myDataset[position].toString())
            Toast.makeText(context, "تم النسخ بنجاح", Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return if (myDataset.isNotEmpty())
            myDataset.size
        else
            0
    }

    private fun setClipboard(text: String) {
        val clipboard =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
    }

    class MyAdapterViewHolder(val context: Context,private val dataBinding: MyTextViewBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(myDataset: String) {
            dataBinding.text.text = myDataset
        }
    }
}