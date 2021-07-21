package com.mpolitakis.movierama.ui.details

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.mpolitakis.movierama.R
import com.mpolitakis.movierama.databinding.DetailsFragmentBinding
import com.mpolitakis.movierama.networking.response.details.similar.Result
import com.mpolitakis.movierama.ui.endlessScroll.EndlessRecyclerOnScrollListener
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private lateinit var viewModel: DetailsViewModel
    private val args : DetailsFragmentArgs by navArgs()
    private lateinit var binding: DetailsFragmentBinding
    private val adapter by lazy { RecyclerViewAdapterSimilar(movieData) }
    private var movieData = mutableListOf<Result>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)


        setUpViews(args.id)
        viewModel.getSimilar(args.id)
        setupRecycleView()
        observeMovieList()
        setUpReviews(args.id)

    }

    private fun observeMovieList() {
        viewModel.moviesSimilar.observe(viewLifecycleOwner, { similarMovies ->

            similarMovies?.let { movieData.addAll(it.results) }

            adapter.refreshData(movieData)


        })
    }

    private fun setupRecycleView() {

        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = HORIZONTAL
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter





        binding.recyclerView.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(current_page: Int) {
                viewModel.getSimilar(current_page)
            }

        })


    }
    private fun setUpReviews(movieId: Int){
        viewModel.getReviews(movieId)

        viewModel.movieReviews.observe(viewLifecycleOwner, {
            if(it.results.size>= 2) {
                binding.reviewerDisplay.text = it.results[0].author
                binding.reviewDisplay.text = it.results[0].content
                binding.reviewDisplay1.text = it.results[1].author
                binding.reviewDisplay1.text = it.results[1].content
            }else if (it.results.size == 1){
                binding.reviewerDisplay.text = it.results[0].author
                binding.reviewDisplay.text = it.results[0].content
            }

        })
    }


    private fun setUpViews(movieId : Int){
        viewModel.getDetails(movieId)
        val pref: SharedPreferences? = requireContext().applicationContext
            .getSharedPreferences("Favourite${movieId}", 0)
        val editor: SharedPreferences.Editor = pref!!.edit()
        viewModel.movieDetails.observe(viewLifecycleOwner, { popularMovies ->



            binding.movieTitleDisplayDetails.text = popularMovies.title
            val picasso: Picasso = Picasso.get()
            picasso.load("http://image.tmdb.org/t/p/original" +popularMovies.poster_path).fit().centerCrop()
                .into(binding.movieIconDisplayDetails)
            binding.rating.rating= (popularMovies.vote_average/2).toFloat()
            binding.releaseDateDisplayDetails.text = popularMovies.release_date
            binding.descriptionText.text = popularMovies.overview
            var genres = ""
            popularMovies.genres.map {
                genres += "${it.name}, "
            }
            binding.genresDisplay.text = genres
            var directorsName  = ""
            popularMovies.credits.crew.forEach{
                if(it.department == "Directing"){
                    directorsName = it.name
                }
            }
            binding.directorDisplay.text = directorsName

            var castNames = ""
            popularMovies.credits.cast.forEach {
                castNames += "${it.name}, "
            }

            binding.castDisplay.text = castNames


            val favouriteItem = "Favourite${popularMovies.id}"
            if (pref.getBoolean(favouriteItem, false)){

                binding.favouriteButtonDetails.setImageResource(R.drawable.favourite_true_foreground)

            }
            else{
                binding.favouriteButtonDetails.setImageResource(R.drawable.favourite_false_foreground)
                editor.apply()

            }
            binding.favouriteButtonDetails.setOnClickListener {
                if (pref.getBoolean(favouriteItem, false)) {
                    editor.putBoolean("Favourite${popularMovies.id}", false)
                    binding.favouriteButtonDetails.setImageResource(R.drawable.favourite_false_foreground)
                    editor.apply()
                    binding.notifyChange()

                } else {
                    editor.putBoolean("Favourite${popularMovies.id}", true)
                    binding.favouriteButtonDetails.setImageResource(R.drawable.favourite_true_foreground)
                    editor.apply()
                    binding.notifyChange()
                }
                binding.executePendingBindings()
            }
    })
    }


}