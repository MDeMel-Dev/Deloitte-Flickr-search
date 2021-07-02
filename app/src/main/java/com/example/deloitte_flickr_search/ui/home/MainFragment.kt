package com.example.deloitte_flickr_search.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deloitte_flickr_search.MainViewModel
import com.example.deloitte_flickr_search.databinding.FragmentHomeBinding

class MainFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var flickrRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel =
            ViewModelProvider(this).get(MainViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        mainViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        // RecyclerView setup
        flickrRecyclerView = binding.recycler
        flickrRecyclerView.layoutManager = GridLayoutManager(context, 3)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}