package com.mpolitakis.movierama.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mpolitakis.movierama.databinding.ItemsSimilarBinding
import com.mpolitakis.movierama.networking.response.details.similar.Result
import com.squareup.picasso.Picasso


class RecyclerViewAdapterSimilar(private var movieList : List<Result>) :
    RecyclerView.Adapter<RecyclerViewAdapterSimilar.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemsSimilarBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    class ViewHolder(private val binding: ItemsSimilarBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(movie: Result) {

            val picasso: Picasso = Picasso.get()

            picasso.load("http://image.tmdb.org/t/p/w500" +movie.poster_path).fit().centerCrop()
                .into(binding.movieIconSimilarDisplay)

            }
        }







     fun refreshData(movies : List<Result>){
        this.movieList = movies
        this.notifyDataSetChanged()
    }



}