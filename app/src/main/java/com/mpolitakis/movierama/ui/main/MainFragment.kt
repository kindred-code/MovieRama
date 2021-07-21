package com.mpolitakis.movierama.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mpolitakis.movierama.R
import com.mpolitakis.movierama.databinding.MainFragmentBinding
import com.mpolitakis.movierama.networking.response.popular.Result
import com.mpolitakis.movierama.ui.endlessScroll.EndlessRecyclerOnScrollListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private val adapter by lazy { RecyclerViewAdapter(movieData) }
    private lateinit var binding: MainFragmentBinding
    private var movieData = mutableListOf<Result>()
    private lateinit var refreshRecyclerViewButton: ImageButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        binding.lifecycleOwner = this




        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val navController = view?.let { Navigation.findNavController(it) }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getList(1)
        observeMovieList()
        setupRecycleView()



        binding.searchButton.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                val action = MainFragmentDirections.actionMainFragmentToSearchFragment(query)
                navController?.navigate(action)
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

    private fun setupRecycleView() {
        val layoutManager = LinearLayoutManager(context)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter





        binding.recyclerView.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(current_page: Int) {
                viewModel.getList(current_page)
            }

        })


    }




}