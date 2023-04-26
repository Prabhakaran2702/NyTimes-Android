package com.prabhakaran.nytimesapp.features.presentation.articlelist

import android.R
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.prabhakaran.nytimesapp.databinding.FragmentItemBinding
import com.prabhakaran.nytimesapp.features.data.model.NytNewsItem
import com.prabhakaran.nytimesapp.features.data.model.Result

class MyItemRecyclerViewAdapter(
    private val clickListener: (NytNewsItem.Article,ImageView) -> Unit,
    private val context: Context,
    private val values: List<NytNewsItem.Article>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.title
        holder.section.text = item.section
        holder.publishedAt.text = item.publishedDate
        holder.byline.text = item.byline


        if(item.media!=null  && item.media.isNotEmpty()){
            Glide.with(context)
                .load(item.media[0].mediaMetadata?.get(0)?.url) // image url
                .placeholder(R.drawable.ic_input_get) // any placeholder to load at start
                .error(R.drawable.stat_notify_error)  // any image in case of error
                .override(200, 200) // resizing
                .centerCrop()
                .into(holder.image)
        }



        holder.parentView.setOnClickListener {
            clickListener(item,holder.image)
        }


    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.titleArticle
        val byline: TextView = binding.bylineArticle
        val section: TextView = binding.section
        val publishedAt: TextView = binding.publishedAt
        val image: ImageView = binding.image
        val parentView: CardView = binding.cardView


        override fun toString(): String {
            return super.toString() + " '" + title.text + "'"
        }
    }

}