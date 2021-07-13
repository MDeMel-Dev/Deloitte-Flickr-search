package com.example.deloitte_flickr_search.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deloitte_flickr_search.MainViewModel
import com.example.deloitte_flickr_search.R
import com.example.deloitte_flickr_search.databinding.FragmentHomeBinding
import com.example.deloitte_flickr_search.models.PhotoItem
import com.example.deloitte_flickr_search.repository.PhotoRepository
import com.example.deloitte_flickr_search.ui.home.recyclerview.FlickrAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {


    private val mainViewModel: MainViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var nestedScrollView : NestedScrollView
    private lateinit var flickrRecyclerView: RecyclerView
    private val adapter = FlickrAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

//        mainViewModel =
////            ViewModelProvider(this.requireActivity(), com.example.deloitte_flickr_search.MainViewModel.Factory(requireActivity()!!.application))
////                .get(MainViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // RecyclerView setup
        flickrRecyclerView = binding.recycler
        flickrRecyclerView.layoutManager = GridLayoutManager(context, 3)
        flickrRecyclerView.adapter = adapter

        //RECYCLERVIEW ADAPTER DATA PASS ON START OR ON SEARCH
        mainViewModel.flickrLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null)
            {
                checkStartState(it)
            }
        })

        //RECYCLERVIEW ADAPTER DATA PASS ON PAGINATION
        mainViewModel.paginateLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null)
            {
                checkPaginationState(it)
            }
        })

        //LISTENER FOR PAGINATION
        doPaging()



        return root
    }

    fun checkStartState(result: PhotoRepository.ViewState){
        when(result)
        {
            is PhotoRepository.ViewState.stateLoaded -> {
                adapter.photoList = result.list as ArrayList<PhotoItem>
                adapter.notifyDataSetChanged()
            }
            is PhotoRepository.ViewState.stateError -> {
                Toast.makeText(context, "Error in fetching photos: ${result.e}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun checkPaginationState(result: PhotoRepository.ViewState){
        when(result)
        {
            is PhotoRepository.ViewState.stateLoaded -> {
                adapter.photoList.addAll(result.list)
                adapter.notifyDataSetChanged()
            }
            is PhotoRepository.ViewState.stateError -> {
                Toast.makeText(context, "Error in fetching photos: ${result.e}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun doPaging()
    {
        flickrRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    Toast.makeText(context,"loading images",Toast.LENGTH_SHORT).show()
                    //INITIATE THE PAGINATION API CALL
                    mainViewModel.paginateAPIData(mainViewModel.currentText , mainViewModel.nextPage )
                    //INCREMENT THE CURRENT PAGE COUNTER BY 1
                    mainViewModel.nextPage +=1
                } else if (!recyclerView.canScrollVertically(-1) && dy < 0) {
                    //scrolled to TOP
                }
            }
        })
    }

    //NAVIGATE TO INFO FRAGMENT
    fun navigateInfo()
    {
        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_home_to_navigation_info)
    }

    //SEARCHVIEW SETUP AND INTEGRATION
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater)
    {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener { override fun onQueryTextSubmit(queryText: String): Boolean {
                Log.d("mane", "QueryTextSubmit: $queryText")
                mainViewModel.currentText = queryText
                mainViewModel.nextPage = 2
                mainViewModel.loadAPIData(queryText)
                return true
            }
                override fun onQueryTextChange(queryText: String): Boolean {
                    Log.d("mane", "QueryTextChange: $queryText")
                    return false
                }
            }) }
    }

    //ACTION BAR android head ITEM ONCLICK
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                navigateInfo()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}