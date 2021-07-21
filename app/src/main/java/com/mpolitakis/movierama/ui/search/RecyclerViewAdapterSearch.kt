package com.mpolitakis.movierama.ui.search

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mpolitakis.movierama.R
import com.mpolitakis.movierama.databinding.ItemMoviesSearchBinding
import com.squareup.picasso.Picasso



class   RecyclerViewAdapterSearch(private var movieList : List<com.mpolitakis.movierama.networking.response.search.Result>) :
    RecyclerView.Adapter<RecyclerViewAdapterSearch.ViewHolder>() {


    lateinit var context: Context



    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMoviesSearchBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding , context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pref: SharedPreferences = context
            .getSharedPreferences("Favourite$position", 0)

        holder.bind(movieList[position], pref ,position)
    }

    override fun getItemCount(): Int = movieList.size

    class ViewHolder(private val binding: ItemMoviesSearchBinding, context: Context) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(movie: com.mpolitakis.movierama.networking.response.search.Result, pref: SharedPreferences, position: Int) {

            val editor: SharedPreferences.Editor = pref.edit()
            binding.movieTitleDisplaySearch.text = movie.title
            val picasso: Picasso = Picasso.get()
            picasso.setIndicatorsEnabled(true)
            picasso.load("http://image.tmdb.org/t/p/w185" +movie.poster_path)
                .into(binding.movieIconDisplaySearch)
            binding.ratingDisplaySearch.text = movie.vote_average.toString()
            binding.releaseDateDisplaySearch.text = movie.release_date

            if (pref.getBoolean("Favourite$position", true)){
                binding.favouriteButtonSearch.setImageResource(R.drawable.favorite_true_foreground)

            }
            else{
                binding.favouriteButtonSearch.setImageResource(R.drawable.favorite_false_foreground)

            }
            binding.favouriteButtonSearch.setOnClickListener {
                if (pref.getBoolean("Favourite$position", true)){
                    editor.putBoolean("Favourite$position", false)
                    binding.favouriteButtonSearch.setImageResource(R.drawable.favorite_false_foreground)
                    editor.apply()

                }
                else{
                    editor.putBoolean("Favourite$position", true)
                    binding.favouriteButtonSearch.setImageResource(R.drawable.favorite_true_foreground)
                    editor.apply()
                }


            }
        }

    }





    fun refreshData(movies : List<com.mpolitakis.movierama.networking.response.search.Result>){
        Log.e("Value","$movieList")
        this.movieList = movies
        this.notifyDataSetChanged()
    }



}