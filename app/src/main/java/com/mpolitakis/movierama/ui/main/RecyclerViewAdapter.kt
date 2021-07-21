package com.mpolitakis.movierama.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mpolitakis.movierama.R
import com.mpolitakis.movierama.databinding.ItemMoviesBinding
import com.mpolitakis.movierama.networking.response.popular.Result
import com.squareup.picasso.Picasso


class   RecyclerViewAdapter(private var movieList : List<Result>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    lateinit var context: Context



    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMoviesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding , context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pref: SharedPreferences = context
            .getSharedPreferences("Favourite$position", 0)

        holder.bind(movieList[position], pref ,position)
    }

    override fun getItemCount(): Int = movieList.size

    class ViewHolder(private val binding: ItemMoviesBinding, context: Context) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(movie: Result, pref: SharedPreferences, position: Int) {

            val editor: SharedPreferences.Editor = pref.edit()
            binding.movieTitleDisplay.text = movie.title
            val picasso: Picasso = Picasso.get()
            picasso.setIndicatorsEnabled(true)
            picasso.load("http://image.tmdb.org/t/p/w185" +movie.poster_path)
                .into(binding.movieIconDisplay)
            binding.ratingDisplay.text = movie.vote_average.toString()
            binding.releaseDateDisplay.text = movie.release_date

            if (pref.getBoolean("Favourite$position", true)){
                binding.favouriteButton.setImageResource(R.drawable.favorite_true_foreground)

            }
            else{
                binding.favouriteButton.setImageResource(R.drawable.favorite_false_foreground)

            }
            binding.favouriteButton.setOnClickListener {
                if (pref.getBoolean("Favourite$position", true)){
                    editor.putBoolean("Favourite$position", false)
                    binding.favouriteButton.setImageResource(R.drawable.favorite_false_foreground)
                    editor.apply()

                }
                else{
                    editor.putBoolean("Favourite$position", true)
                    binding.favouriteButton.setImageResource(R.drawable.favorite_true_foreground)
                    editor.apply()
                }


            }
        }

    }





     fun refreshData(movies : List<Result>){
        Log.e("Value","$movieList")
        this.movieList = movies
        this.notifyDataSetChanged()
    }



}