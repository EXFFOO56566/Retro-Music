package com.tochy.odikwa.retromusic.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tochy.odikwa.retromusic.R
import com.tochy.odikwa.retromusic.extensions.hide
import com.tochy.odikwa.retromusic.model.Contributor
import com.tochy.odikwa.retromusic.util.RetroUtil
import com.tochy.odikwa.retromusic.views.RetroShapeableImageView

class TranslatorsAdapter(
    private var contributors: List<Contributor>
) : RecyclerView.Adapter<TranslatorsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_contributor,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contributor = contributors[position]
        holder.bindData(contributor)
        holder.itemView.setOnClickListener {
            RetroUtil.openUrl(it?.context as Activity, contributors[position].link)
        }
    }

    override fun getItemCount(): Int {
        return contributors.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val text: TextView = itemView.findViewById(R.id.text)
        val image: RetroShapeableImageView = itemView.findViewById(R.id.icon)

        internal fun bindData(contributor: Contributor) {
            title.text = contributor.name
            text.text = contributor.summary
            image.hide()
        }
    }
}