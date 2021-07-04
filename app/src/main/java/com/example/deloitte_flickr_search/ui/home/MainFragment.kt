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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deloitte_flickr_search.MainViewModel
import com.example.deloitte_flickr_search.R
import com.example.deloitte_flickr_search.models.PhotoItem
import com.example.deloitte_flickr_search.databinding.FragmentHomeBinding
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



        nestedScrollView = binding.recyclerNest
        // RecyclerView setup
        flickrRecyclerView = binding.recycler
        flickrRecyclerView.layoutManager = GridLayoutManager(context, 3)
        flickrRecyclerView.adapter = adapter

        //RECYCLERVIEW ADAPTER DATA PASS ON START OR ON SEARCH
        mainViewModel.flickrLiveData.observe(viewLifecycleOwner, Observer {
           if(!(it == null)) {
               adapter.photoList = it as ArrayList<PhotoItem>
               adapter.notifyDataSetChanged()
           }
        })

        //RECYCLERVIEW ADAPTER DATA PASS ON PAGINATION
        mainViewModel.paginateLiveData.observe(viewLifecycleOwner, Observer {
            if(!(it == null)) {
                adapter.photoList.addAll(it as ArrayList<PhotoItem>)
//                adapter.notifyItemRangeInserted((mainViewModel.nextPage-1)*100 , 100)
                adapter.notifyDataSetChanged()
            }
        })

        //LISTENER FOR PAGINATION
        doPaging()



        return root
    }



    fun doPaging()
    {
        nestedScrollView!!.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                Toast.makeText(this.context,"loading images",Toast.LENGTH_SHORT).show()
                //INITIATE THE PAGINATION API CALL
                mainViewModel.paginateAPIData(mainViewModel.currentText , mainViewModel.nextPage , requireActivity()!!.application)
                //INCREMENT THE CURRENT PAGE COUNTER BY 1
                mainViewModel.nextPage +=1
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
                mainViewModel.loadAPIData(queryText , requireActivity()!!.application)
                return true
            }
                override fun onQueryTextChange(queryText: String): Boolean {
                    Log.d("mane", "QueryTextChange: $queryText")
                    return false
                }
            }) }
    }

    //ACTION BAR HOME ITEM ONCLICK
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