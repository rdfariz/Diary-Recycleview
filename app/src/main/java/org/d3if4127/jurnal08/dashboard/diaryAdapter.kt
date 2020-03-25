package org.d3if4127.jurnal08.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.diary_item.view.*
import org.d3if4127.jurnal08.R
import org.d3if4127.jurnal08.database.Diary


class diaryAdapter(private val myDataset: List<Diary>) :
    RecyclerView.Adapter<diaryAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val people_item = LayoutInflater.from(parent.context)
            .inflate(R.layout.diary_item, parent, false)
        return MyViewHolder(
            people_item
        )
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.tv_tanggal.text = myDataset[position].lastUpdate.toString()
        holder.view.tv_content.text = myDataset[position].content
        holder.view.setOnClickListener {
            var bundle = bundleOf("content" to myDataset[position].content, "id" to myDataset[position].id)
            holder.view.findNavController().navigate(R.id.action_dashboardFragment_to_updateFragment, bundle)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}