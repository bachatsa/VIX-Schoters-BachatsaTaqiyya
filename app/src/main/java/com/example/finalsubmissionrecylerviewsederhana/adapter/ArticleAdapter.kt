package com.example.finalsubmissionrecylerviewsederhana.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.util.Util
import com.example.finalsubmissionrecylerviewsederhana.R
import com.example.finalsubmissionrecylerviewsederhana.databinding.AdapterListBinding
import com.example.finalsubmissionrecylerviewsederhana.model.Article

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHodler>() {

    companion object{
        private val diffUtilCallback =  object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
               return newItem.title == oldItem.title
            }
        }
    }
    val differ = AsyncListDiffer(this, diffUtilCallback)
    class ArticleViewHodler(var view : AdapterListBinding): RecyclerView.ViewHolder(view.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHodler {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<AdapterListBinding>(inflater, R.layout.adapter_list,parent,false)
        return ArticleViewHodler(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHodler, position: Int) {
        val article = differ.currentList[position]
        holder.view.article = article

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                article.let {
                    it(article)
                }
            }
        }

        holder.view.imgShare.setOnClickListener {
            onItemShareListener?.let {
                article.let { it1 ->
                    it(it1)
                }
            }
        }

        holder.view.imgBookmark.setOnClickListener {
//            if (holder.view.imgBookmark.toString().toInt() == 0) {
//                holder.view.imgBookmark.tag = 1
//                holder.view.imgBookmark.setImageDrawable(it.resources.getDrawable(R.drawable.ic_baseline_bookmark_added_24))
//                onItemSaveListener?.let {
//                    if (article != null) { it(article)
//                    }
//                }
//            }
//            else {
//                holder.view.imgBookmark.tag = 0
//                holder.view.imgBookmark.setImageDrawable(it.resources.getDrawable(R.drawable.ic_baseline_bookmark_24))
//                onItemSaveListener?.let {
//                    if (article != null) {
//                        it(article)
//                    }
//                }
//            }
            onItemSaveListener.let {
                article?.let { it1 ->
                    if (it != null) { it(it1) }
                }
            }
        }
    }

    override fun getItemCount() =  differ.currentList.size
    var isSave = false

    override fun getItemId(position: Int) = position.toLong()



    private var onItemClickListener : ((Article) -> Unit)? = null
    private var onItemSaveListener : ((Article) -> Unit)? = null
    private var onItemDeleteListener : ((Article) -> Unit)? = null
    private var onItemShareListener : ((Article) -> Unit)? = null

    fun setOnclickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }
    fun setSaveListener(listener: (Article) -> Unit){
        onItemSaveListener = listener
    }
    fun setDeleteListener(listener: (Article) -> Unit){
        onItemDeleteListener = listener
    }
    fun setShareListener(listener: (Article) -> Unit){
        onItemShareListener = listener

    }

}