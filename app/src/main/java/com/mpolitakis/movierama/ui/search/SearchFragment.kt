package com.mpolitakis.movierama.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpolitakis.movierama.R
import com.mpolitakis.movierama.databinding.SearchFragmentBinding
import com.mpolitakis.movierama.networking.response.search.Result
import com.mpolitakis.movierama.ui.endlessScroll.EndlessRecyclerOnScrollListener
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel
    private val adapter by lazy { RecyclerViewAdapterSearch(movieData) }
    private lateinit var binding: SearchFragmentBinding
    private var movieData = mutableListOf<Result>()
    private lateinit var refreshRecyclerViewButton: ImageButton
    private val args : SearchFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false)

        binding.lifecycleOwner = this




        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val navController = view?.let { Navigation.findNavController(it) }
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        viewModel.getList(1, args.query)
        observeMovieList()
        setupRecycleView(args.query)


        binding.searchButton.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                movieData.clear()

                viewModel.getList(1, query)
                setupRecycleView(query)
                adapter.notifyDataSetChanged()
                return false
            }

        })





    }

    private fun observeMovieList() {
        viewModel.movieList.observe(viewLifecycleOwner, { popularMovies ->

            popularMovies?.let { movieData.addAll(it.results) }

            adapter.refreshData(movieData)


        })
    }

    private fun setupRecycleView(query : String) {
        val layoutManager = LinearLayoutManager(context)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter





        binding.recyclerView.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(current_page: Int) {
                viewModel.getList(current_page, query)
            }

        })

    }}




