package com.example.finalsubmissionrecylerviewsederhana.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalsubmissionrecylerviewsederhana.R
import com.example.finalsubmissionrecylerviewsederhana.databinding.AdapterListBinding
import com.example.finalsubmissionrecylerviewsederhana.databinding.AdapterSavedNewsBinding
import com.example.finalsubmissionrecylerviewsederhana.model.Article

class SavedAdapter : RecyclerView.Adapter<SavedAdapter.ArticleViewHodler>() {

        private val diffUtilCallback =  object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == oldItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }
    }
    val differ = AsyncListDiffer(this, diffUtilCallback)


    class ArticleViewHodler(var view : AdapterSavedNewsBinding): RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHodler {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<AdapterSavedNewsBinding>(inflater, R.layout.adapter_saved_news,parent,false)
        return ArticleViewHodler(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHodler, position: Int) {
        val article = differ.currentList[position]
        holder.view.article = article

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                article.let{
                    it(article)
                }
            }
        }

        holder.view.imgShare.setOnClickListener {
            onItemShareListener?.let {
                article.let{ it1 ->
                    it(it1)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private var onItemClickListener : ((Article) -> Unit)? = null
    private var onItemShareListener : ((Article) -> Unit)? = null

    fun setOnclickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }
    fun setShareListener(listener: (Article) -> Unit){
        onItemShareListener = listener

    }

}