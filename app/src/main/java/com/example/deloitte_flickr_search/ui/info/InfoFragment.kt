package com.example.deloitte_flickr_search.ui.info

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.deloitte_flickr_search.R
import com.example.deloitte_flickr_search.databinding.FragmentInfoBinding

class InfoFragment : Fragment() {


    private var _binding: FragmentInfoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //PROFILE-LINK ACTION ON CLICK
        val profileLink = binding.profileLink
        profileLink.movementMethod = LinkMovementMethod.getInstance()


        return root
    }

    //NAVIGATE TO HOME FRAGMENT
    fun navigateHome()
    {
        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_info_to_navigation_home)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.info_menu, menu)
    }

    //ACTION BAR ITEM HOME ONCLICK
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_home -> {
                navigateHome()
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