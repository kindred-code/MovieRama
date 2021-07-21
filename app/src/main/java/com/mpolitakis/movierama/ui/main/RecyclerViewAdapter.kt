package com.mpolitakis.movierama.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mpolitakis.movierama.R
import com.mpolitakis.movierama.databinding.ItemMoviesBinding
import com.mpolitakis.movierama.networking.response.popular.Result
import com.squareup.picasso.Picasso


class   RecyclerViewAdapter(private var movieList: List<Result>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    lateinit var context: Context

    lateinit var sharedPreferences: SharedPreferences

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMoviesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        sharedPreferences = context.applicationContext
            .getSharedPreferences("Favourite${movieList[position].id}", Context.MODE_PRIVATE)


        holder.itemView.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(movieList[position].id)
            val navController = Navigation.findNavController(it)
            navController.navigate(action)
        }

        holder.bind(movieList[position], sharedPreferences)
    }

    override fun getItemCount(): Int = movieList.size

    class ViewHolder(private val binding: ItemMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(movie: Result, pref: SharedPreferences) {

            val editor: SharedPreferences.Editor = pref.edit()
            binding.movieTitleDisplay.text = movie.title
            val picasso: Picasso = Picasso.get()
            picasso.load("http://image.tmdb.org/t/p/w500" +movie.poster_path).fit().centerCrop()
                .into(binding.movieIconDisplay)
            binding.rating.rating= (movie.vote_average/2).toFloat()
            binding.releaseDateDisplay.text = movie.release_date
            val favouriteItem = "Favourite${movie.id}"
            if (pref.getBoolean(favouriteItem, false)){
                binding.favouriteButton.setImageResource(R.drawable.favourite_true_foreground)
            }
            else{
                editor.putBoolean("Favourite${movie.id}", false)
                binding.favouriteButton.setImageResource(R.drawable.favourite_false_foreground)
                editor.apply()

            }
            binding.favouriteButton.setOnClickListener {
                if (pref.getBoolean(favouriteItem, false)){
                    editor.putBoolean("Favourite${movie.id}", false)
                    binding.favouriteButton.setImageResource(R.drawable.favourite_false_foreground)
                    editor.apply()
                    binding.notifyChange()

                }
                else{
                    editor.putBoolean("Favourite${movie.id}", true)
                    binding.favouriteButton.setImageResource(R.drawable.favourite_true_foreground)
                    editor.apply()
                    binding.notifyChange()
                }


            }
        }

    }





     fun refreshData(movies : List<Result>){
        this.movieList = movies
        this.notifyDataSetChanged()
    }



}