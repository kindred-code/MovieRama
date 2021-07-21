package com.mpolitakis.movierama.ui.search

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mpolitakis.movierama.R
import com.mpolitakis.movierama.databinding.ItemMoviesSearchBinding
import com.mpolitakis.movierama.networking.response.search.Result
import com.squareup.picasso.Picasso


class   RecyclerViewAdapterSearch(private var movieList : List<Result>) :
    RecyclerView.Adapter<RecyclerViewAdapterSearch.ViewHolder>() {


    lateinit var context: Context



    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMoviesSearchBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pref: SharedPreferences = context.applicationContext
            .getSharedPreferences("Favourite${movieList[position].id}", 0)

        holder.itemView.setOnClickListener{
            val action = SearchFragmentDirections.actionSearchFragmentToDetailsFragment(movieList[position].id)
            val navController = Navigation.findNavController(it)
            navController.navigate(action)
        }
        holder.bind(movieList[position], pref)
    }

    override fun getItemCount(): Int = movieList.size

    class ViewHolder(private val binding: ItemMoviesSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(movie: Result, pref: SharedPreferences) {

            val editor: SharedPreferences.Editor = pref.edit()
            binding.movieTitleDisplaySearch.text = movie.title
            val picasso: Picasso = Picasso.get()
            picasso.setIndicatorsEnabled(true)
            picasso.load("http://image.tmdb.org/t/p/w500" +movie.poster_path).fit().centerCrop()
                .into(binding.movieIconDisplaySearch)
            binding.ratingSearch.rating= (movie.vote_average/2).toFloat()
            binding.releaseDateDisplaySearch.text = movie.release_date
            val favouriteItem = "Favourite${movie.id}"
            if (pref.getBoolean(favouriteItem, false)){

                binding.favouriteButtonSearch.setImageResource(R.drawable.favourite_true_foreground)

            }
            else{

                binding.favouriteButtonSearch.setImageResource(R.drawable.favourite_false_foreground)
                editor.apply()

            }
            binding.favouriteButtonSearch.setOnClickListener {
                if (pref.getBoolean(favouriteItem, false)){
                    editor.putBoolean("Favourite${movie.id}", false)
                    binding.favouriteButtonSearch.setImageResource(R.drawable.favourite_false_foreground)
                    editor.apply()
                    binding.notifyChange()

                }
                else{
                    editor.putBoolean("Favourite${movie.id}", true)
                    binding.favouriteButtonSearch.setImageResource(R.drawable.favourite_true_foreground)
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