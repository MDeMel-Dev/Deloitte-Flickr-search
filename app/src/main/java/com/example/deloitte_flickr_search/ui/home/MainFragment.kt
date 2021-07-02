package com.example.deloitte_flickr_search.ui.home

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deloitte_flickr_search.MainViewModel
import com.example.deloitte_flickr_search.R
import com.example.deloitte_flickr_search.data.PhotoItem
import com.example.deloitte_flickr_search.databinding.FragmentHomeBinding
import com.example.deloitte_flickr_search.ui.home.recyclerview.FlickrAdapter

class MainFragment : Fragment() {


    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var flickrRecyclerView: RecyclerView

    val adapter = FlickrAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        mainViewModel =
            ViewModelProvider(this.requireActivity(), com.example.deloitte_flickr_search.MainViewModel.Factory(requireActivity()!!.application))
                .get(MainViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // RecyclerView setup
        flickrRecyclerView = binding.recycler
        flickrRecyclerView.layoutManager = GridLayoutManager(context, 3)
        flickrRecyclerView.adapter = adapter

        mainViewModel.flickrLiveData.observe(viewLifecycleOwner, Observer {
           if(!(it == null)) {
               adapter.photoList = it as ArrayList<PhotoItem>
               adapter.notifyDataSetChanged()
           }
        })

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener { override fun onQueryTextSubmit(queryText: String): Boolean {
                Log.d("mane", "QueryTextSubmit: $queryText")
                mainViewModel.loadAPIData(queryText , requireActivity()!!.application)
                return true
            }
                override fun onQueryTextChange(queryText: String): Boolean {
                    Log.d("mane", "QueryTextChange: $queryText")
                    return false
                }
            }) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}